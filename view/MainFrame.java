package view;

import controller.BookingController;
import controller.CarController;
import controller.UserController;
import model.Booking;
import model.Car;
import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * MainFrame
 * - Single main class for the Swing UI (keeps views in separate inner classes for simplicity)
 * - This file uses controller.* APIs to talk with the DB.
 *
 * NOTE: For a clearer MVC split you can move each inner class to its own file.
 */
public class MainFrame extends JFrame {
    // Colors and styles (shortened)
    public static final Color NAVY_HEADER = new Color(28, 40, 51);
    public static final Color PRIMARY_BLUE = new Color(37, 150, 190);
    public static final Color ACCENT_GREEN = new Color(55, 160, 80);
    public static final Color ACCENT_RED = new Color(200, 50, 50);
    public static final Color ACCENT_ORANGE = new Color(255, 140, 0);
    public static final Color BACKGROUND_LIGHT = new Color(245, 248, 250);
    public static final Color CARD_BORDER = new Color(210, 215, 220);

    private CardLayout cards = new CardLayout();
    private JPanel cardPanel = new JPanel(cards);
    private LoginPanel loginPanel;
    private DashboardPanel dashboardPanel;
    private User currentUser;
    private DecimalFormat MONEY = new DecimalFormat("#,###.##");
    public static final String IMAGE_FOLDER = "images";
    public static final String PLACEHOLDER = IMAGE_FOLDER + "/placeholder.png";

    public MainFrame() {
        setTitle("Car Rental — MVC Demo");
        setSize(1100,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        UIUtils.ensureImagesFolder();

        loginPanel = new LoginPanel(this);
        dashboardPanel = new DashboardPanel(this);

        cardPanel.add(loginPanel,"login");
        cardPanel.add(dashboardPanel,"dashboard");
        add(cardPanel);
        showLogin();
    }

    public void showLogin(){ cards.show(cardPanel,"login"); }
    public void showDashboard(User u){ currentUser=u; dashboardPanel.setUser(u); cards.show(cardPanel,"dashboard"); }
    public void refreshAll(){ dashboardPanel.refreshAll(); }

    // ---------------- Login Panel ----------------
    class LoginPanel extends JPanel {
        private JTextField txtUser; private JPasswordField txtPass; private JComboBox<String> roleBox;
        private MainFrame parent;
        public LoginPanel(MainFrame parent){
            this.parent=parent;
            setLayout(new BorderLayout()); setBackground(BACKGROUND_LIGHT);
            add(UIUtils.makeHeader("Car Rental & Dealership"),BorderLayout.NORTH);
            JPanel center = new JPanel(new GridBagLayout()); center.setBorder(new EmptyBorder(24,120,24,120));
            center.setBackground(BACKGROUND_LIGHT);
            GridBagConstraints gbc = new GridBagConstraints(); gbc.insets=new Insets(10,10,10,10); gbc.fill=GridBagConstraints.HORIZONTAL;
            gbc.gridx=0; gbc.gridy=0; center.add(new JLabel("Username:"),gbc);
            gbc.gridx=1; txtUser = new JTextField(22); center.add(txtUser,gbc);
            gbc.gridx=0; gbc.gridy=1; center.add(new JLabel("Password:"),gbc);
            gbc.gridx=1; txtPass = new JPasswordField(22); center.add(txtPass,gbc);
            gbc.gridx=0; gbc.gridy=2; center.add(new JLabel("Role:"),gbc);
            gbc.gridx=1; roleBox = new JComboBox<>(new String[]{"user","seller","admin"}); center.add(roleBox,gbc);
            gbc.gridx=0; gbc.gridy=3; gbc.gridwidth=2;
            JPanel pbtn = new JPanel(); pbtn.setBackground(BACKGROUND_LIGHT);
            JButton btnLogin = new JButton("Login"); btnLogin.setBackground(PRIMARY_BLUE); btnLogin.setForeground(Color.WHITE); btnLogin.setFocusPainted(false);
            btnLogin.addActionListener(e->doLogin());
            JButton btnSignup = new JButton("Create Account"); btnSignup.setBackground(ACCENT_GREEN); btnSignup.setForeground(Color.WHITE); btnSignup.setFocusPainted(false);
            btnSignup.addActionListener(e->doSignup());
            pbtn.add(btnLogin); pbtn.add(btnSignup); center.add(pbtn,gbc);
            add(center,BorderLayout.CENTER);
        }

        private void doLogin(){
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword());
            String role = (String)roleBox.getSelectedItem();
            if(u.isEmpty()||p.isEmpty()){ JOptionPane.showMessageDialog(this,"Enter credentials.","Validation",JOptionPane.WARNING_MESSAGE); return; }
            var found = UserController.authenticate(u,p,role);
            if(found.isPresent()){
                String otp = JOptionPane.showInputDialog(this,
                        "We are facing an SMS issue.\nPlease use 910298 as your OTP:",
                        "OTP Verification", JOptionPane.INFORMATION_MESSAGE);
                if(otp != null && otp.equals("910298")){
                    parent.showDashboard(found.get());
                    txtPass.setText("");
                } else {
                    JOptionPane.showMessageDialog(this,"Invalid OTP! Login failed.","Login Failed",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,"Invalid credentials or role.","Login Failed",JOptionPane.ERROR_MESSAGE);
            }
        }

        private void doSignup(){
            JTextField username = new JTextField();
            JPasswordField password = new JPasswordField();
            JComboBox<String> role = new JComboBox<>(new String[]{"user","seller"});
            JTextField contact = new JTextField();
            Object[] fields = {"Username:",username,"Password:",password,"Role:",role,"Contact:",contact};
            int ans = JOptionPane.showConfirmDialog(this,fields,"Create Account",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(ans==JOptionPane.OK_OPTION){
                String u=username.getText().trim();
                String p=new String(password.getPassword());
                String r=(String)role.getSelectedItem();
                String c=contact.getText().trim();
                if(u.isEmpty()||p.isEmpty()||c.isEmpty()){ JOptionPane.showMessageDialog(this,"Fill all fields.","Validation",JOptionPane.WARNING_MESSAGE); return; }
                if(UserController.usernameExists(u)){ JOptionPane.showMessageDialog(this,"Username already exists!","Validation",JOptionPane.WARNING_MESSAGE); return; }
                User newUser = new User("U"+System.currentTimeMillis()%100000, u, p, r, c);
                UserController.addUser(newUser);
                JOptionPane.showMessageDialog(this,"Account created! You can login now.","Success",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // ---------------- Dashboard ----------------
    class DashboardPanel extends JPanel {
        private MainFrame parent; private User user; private JTabbedPane tabs;
        private CardBrowsePanel browsePanel; private SellerPanel sellerPanel; private AdminPanel adminPanel; private BookingHistoryPanel historyPanel; private AllBookingsPanel allBookingsPanel;
        public DashboardPanel(MainFrame parent){
            this.parent=parent; setLayout(new BorderLayout());
            JPanel top = new JPanel(new BorderLayout()); top.setBorder(new EmptyBorder(8,12,8,12)); top.setBackground(Color.WHITE);
            JLabel lbl = new JLabel("Dashboard",SwingConstants.LEFT); lbl.setFont(new Font("Segoe UI",Font.BOLD,18)); top.add(lbl,BorderLayout.WEST);
            JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT)); rightControls.setBackground(Color.WHITE);
            JButton logout = new JButton("Logout"); logout.addActionListener(e->parent.showLogin()); rightControls.add(logout); top.add(rightControls,BorderLayout.EAST);
            add(top,BorderLayout.NORTH);
            tabs = new JTabbedPane(); tabs.setBackground(BACKGROUND_LIGHT);
            browsePanel = new CardBrowsePanel(parent);
            sellerPanel = new SellerPanel(parent);
            adminPanel = new AdminPanel(parent);
            historyPanel = new BookingHistoryPanel(parent);
            allBookingsPanel = new AllBookingsPanel(parent);
            add(tabs,BorderLayout.CENTER);
        }
        public void setUser(User user){
            this.user=user;
            tabs.removeAll();
            tabs.addTab("Browse Cars",browsePanel);
            if(user.role.equals("user")) tabs.addTab("My Bookings", historyPanel);
            switch(user.role.toLowerCase()){
                case "seller":
                    tabs.addTab("My Inventory", sellerPanel);
                    tabs.addTab("Car Bookings", allBookingsPanel);
                    break;
                case "admin":
                    tabs.addTab("Manage Cars", sellerPanel);
                    tabs.addTab("Users", adminPanel);
                    tabs.addTab("All Bookings", allBookingsPanel);
                    break;
            }
            browsePanel.setCurrentUser(user);
            sellerPanel.setCurrentUser(user);
            historyPanel.setCurrentUser(user);
            allBookingsPanel.setCurrentUser(user);
            refreshAll();
        }
        public void refreshAll(){
            browsePanel.refresh();
            sellerPanel.refresh();
            adminPanel.refresh();
            allBookingsPanel.refresh();
            if(user!=null && user.role.equals("user")) historyPanel.refresh();
        }
    }

    // ---------------- Card Browse Panel (simplified) ----------------
    class CardBrowsePanel extends JPanel {
        private MainFrame parent; private JPanel cardContainer; private JScrollPane scrollPane; private JTextField txtSearch; private User currentUser;
        public CardBrowsePanel(MainFrame parent){
            this.parent=parent; setLayout(new BorderLayout()); setBackground(BACKGROUND_LIGHT); setBorder(new EmptyBorder(10,10,10,10));
            JLabel header = new JLabel("🚗 Car Inventory", SwingConstants.CENTER); header.setFont(new Font("Segoe UI", Font.BOLD, 22)); header.setOpaque(true); header.setBackground(NAVY_HEADER); header.setForeground(Color.WHITE); header.setBorder(new EmptyBorder(12,12,12,12));
            JPanel topPanel = new JPanel(new BorderLayout(4,4)); topPanel.setBackground(BACKGROUND_LIGHT); topPanel.add(header, BorderLayout.NORTH);
            JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); searchPanel.setBackground(BACKGROUND_LIGHT); searchPanel.add(new JLabel("Search:")); txtSearch = new JTextField(20); searchPanel.add(txtSearch);
            JButton btnSearch = new JButton("Go"); btnSearch.setBackground(PRIMARY_BLUE); btnSearch.setForeground(Color.WHITE); btnSearch.addActionListener(e -> refresh()); searchPanel.add(btnSearch); topPanel.add(searchPanel, BorderLayout.SOUTH);
            add(topPanel, BorderLayout.NORTH);
            cardContainer = new JPanel(); cardContainer.setLayout(new WrapLayout(FlowLayout.LEFT,16,16)); cardContainer.setBackground(BACKGROUND_LIGHT);
            scrollPane = new JScrollPane(cardContainer); scrollPane.getVerticalScrollBar().setUnitIncrement(16); scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(scrollPane, BorderLayout.CENTER);
        }
        public void setCurrentUser(User u){ currentUser = u; }
        public void refresh(){
            String query = txtSearch.getText().trim().toLowerCase();
            cardContainer.removeAll();
            List<Car> cars = CarController.fetchCars();
            for(Car c : cars){
                if(!query.isEmpty() && !(c.name.toLowerCase().contains(query) || c.category.toLowerCase().contains(query))) continue;
                JPanel card = new JPanel(new BorderLayout(4,4)); card.setPreferredSize(new Dimension(240,360)); card.setBackground(Color.WHITE); card.setBorder(BorderFactory.createLineBorder(CARD_BORDER,1,true));
                JLabel imgLabel = new JLabel(); imgLabel.setHorizontalAlignment(SwingConstants.CENTER); imgLabel.setIcon(UIUtils.loadScaledIcon(c.imagePath, 220, 140)); card.add(imgLabel, BorderLayout.NORTH);
                JPanel info = new JPanel(); info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS)); info.setBorder(new EmptyBorder(6,6,6,6));
                JLabel lblName = new JLabel(c.name + " (" + c.model + ")"); lblName.setFont(new Font("Segoe UI", Font.BOLD, 14)); info.add(lblName);
                int stars = 3 + new Random().nextInt(3); StringBuilder sb = new StringBuilder(); for(int i=0;i<stars;i++) sb.append("★"); for(int i=stars;i<5;i++) sb.append("☆");
                JLabel lblRating = new JLabel(sb.toString()); lblRating.setForeground(ACCENT_ORANGE); info.add(lblRating);
                info.add(new JLabel("Seats: " + c.seats + " | Fuel: " + c.fuelType + " | Transmission: " + c.transmission));
                info.add(new JLabel("Category: " + c.category));
                info.add(new JLabel("Price/day: ₹" + MONEY.format(c.pricePerDay)));
                JLabel lblStatus = new JLabel("Status: " + c.status); lblStatus.setForeground("Available".equalsIgnoreCase(c.status) ? ACCENT_GREEN : ACCENT_RED); info.add(lblStatus);
                if(currentUser.role.equals("admin")){ User owner = null; if(c.ownerId!=null) owner = UserController.fetchUsers().stream().filter(u->u.id.equals(c.ownerId)).findFirst().orElse(null); info.add(new JLabel("Owner: " + (owner!=null?owner.username:"Unknown"))); }
                if(currentUser.role.equals("user")){
                    JButton bookBtn = new JButton("Book"); bookBtn.setBackground(PRIMARY_BLUE); bookBtn.setForeground(Color.WHITE); bookBtn.setEnabled("Available".equalsIgnoreCase(c.status));
                    bookBtn.addActionListener(e -> { BookingDialog bd = new BookingDialog(MainFrame.this, c, currentUser); bd.setVisible(true); parent.refreshAll(); });
                    info.add(bookBtn);
                }
                card.add(info, BorderLayout.CENTER);
                cardContainer.add(card);
            }
            cardContainer.revalidate(); cardContainer.repaint();
        }
    }

    // ---------------- Booking Dialog ----------------
    class BookingDialog extends JDialog {
        public BookingDialog(JFrame parent, Car car, User user){
            super(parent,"Book Car: " + car.name + " (" + car.model + ")",true);
            setSize(750,500); setLocationRelativeTo(parent); setLayout(new BorderLayout(15,15)); getRootPane().setBorder(new EmptyBorder(15,15,15,15));
            JPanel carInfoPanel = new JPanel(new BorderLayout(10,10)); carInfoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CARD_BORDER), "Vehicle Details")); carInfoPanel.setBackground(BACKGROUND_LIGHT);
            JLabel imgLabel = new JLabel(); imgLabel.setHorizontalAlignment(SwingConstants.CENTER); imgLabel.setIcon(UIUtils.loadScaledIcon(car.imagePath, 300, 180)); carInfoPanel.add(imgLabel, BorderLayout.NORTH);
            JPanel featuresPanel = new JPanel(new GridLayout(0,1)); featuresPanel.setBackground(BACKGROUND_LIGHT); featuresPanel.setBorder(new EmptyBorder(10,5,5,5));
            featuresPanel.add(new JLabel("<html><b>Category:</b> " + car.category + "</html>"));
            featuresPanel.add(new JLabel("<html><b>Price/Day:</b> ₹" + MONEY.format(car.pricePerDay) + "</html>"));
            featuresPanel.add(new JLabel("<html><b>Fuel:</b> " + car.fuelType + "</html>"));
            featuresPanel.add(new JLabel("<html><b>Seats:</b> " + car.seats + "</html>"));
            featuresPanel.add(new JLabel("<html><b>Transmission:</b> " + car.transmission + "</html>"));
            carInfoPanel.add(featuresPanel, BorderLayout.CENTER);
            add(carInfoPanel, BorderLayout.WEST);
            JPanel inputPanel = new JPanel(new GridBagLayout()); inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(CARD_BORDER), "Booking Information")); inputPanel.setBackground(BACKGROUND_LIGHT);
            GridBagConstraints gbc = new GridBagConstraints(); gbc.insets = new Insets(10,10,10,10); gbc.fill = GridBagConstraints.HORIZONTAL; gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx=0; gbc.gridy=0; inputPanel.add(new JLabel("Pickup Location:"),gbc); gbc.gridx=1; JTextField txtPlace=new JTextField(car.category+" Rent Location",25); inputPanel.add(txtPlace,gbc);
            gbc.gridx=0; gbc.gridy=1; inputPanel.add(new JLabel("Pickup Date (dd/MM/yyyy):"),gbc); gbc.gridx=1; JTextField txtDate=new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())); inputPanel.add(txtDate,gbc);
            gbc.gridx=0; gbc.gridy=2; inputPanel.add(new JLabel("Pickup Time (HH:mm):"),gbc); gbc.gridx=1; JTextField txtTime=new JTextField(new SimpleDateFormat("HH:mm").format(new java.util.Date())); inputPanel.add(txtTime,gbc);
            gbc.gridx=0; gbc.gridy=3; inputPanel.add(new JLabel("No. of Days:"),gbc); gbc.gridx=1; JSpinner spinDays = new JSpinner(new SpinnerNumberModel(1,1,30,1)); inputPanel.add(spinDays,gbc);
            gbc.gridx=0; gbc.gridy=4; gbc.gridwidth=2; JLabel lblTotal = new JLabel("Total Price: ₹" + MONEY.format(car.pricePerDay)); lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD,14f)); lblTotal.setForeground(NAVY_HEADER); inputPanel.add(lblTotal,gbc);
            spinDays.addChangeListener(e -> { int days = (Integer)spinDays.getValue(); lblTotal.setText("Total Price: ₹" + MONEY.format(car.pricePerDay * days)); });
            gbc.gridy=5; JButton confirm = new JButton("Confirm Booking"); confirm.setBackground(ACCENT_GREEN); confirm.setForeground(Color.WHITE);
            confirm.addActionListener(e -> {
                int days = (Integer)spinDays.getValue(); double total = car.pricePerDay * days;
                Booking b = new Booking("B"+System.currentTimeMillis()%100000, car.id, user.id, txtPlace.getText().trim(), txtDate.getText().trim(), txtTime.getText().trim(), days, total, "Pending");
                BookingController.addBooking(b);
                JOptionPane.showMessageDialog(this,"Booking sent for approval!","Pending Approval",JOptionPane.INFORMATION_MESSAGE);
                dispose();
            });
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); buttonPanel.setBackground(BACKGROUND_LIGHT); buttonPanel.add(confirm);
            gbc.gridy=6; gbc.gridx=0; gbc.gridwidth=2; inputPanel.add(buttonPanel,gbc);
            add(inputPanel, BorderLayout.CENTER);
        }
    }

    // ---------------- Seller Panel ----------------
    class SellerPanel extends JPanel {
        private JTable tbl; private DefaultTableModel model; private User currentUser;
        public SellerPanel(MainFrame parent){
            setLayout(new BorderLayout()); setBackground(BACKGROUND_LIGHT);
            add(UIUtils.makeHeader("My Inventory"), BorderLayout.NORTH);
            model = new DefaultTableModel(new String[]{"ID","Name","Model","Price/Day","Status","Fuel","Seats","Transmission"},0){
                public boolean isCellEditable(int r,int c){return false;}
            };
            tbl = new JTable(model); tbl.getTableHeader().setBackground(NAVY_HEADER); tbl.getTableHeader().setForeground(Color.WHITE); tbl.setRowHeight(25);
            add(new JScrollPane(tbl),BorderLayout.CENTER);
            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT)); bottom.setBackground(BACKGROUND_LIGHT);
            JButton addBtn = new JButton("Add Car"); addBtn.setBackground(ACCENT_GREEN); addBtn.setForeground(Color.WHITE); addBtn.addActionListener(e->addOrEditCar(null));
            JButton editBtn = new JButton("Edit Car"); editBtn.setBackground(PRIMARY_BLUE); editBtn.setForeground(Color.WHITE); editBtn.addActionListener(e->{int r=tbl.getSelectedRow(); if(r>=0) addOrEditCar(getSelectedCar(r));});
            JButton delBtn = new JButton("Delete Car"); delBtn.setBackground(ACCENT_RED); delBtn.setForeground(Color.WHITE); delBtn.addActionListener(e->{int r=tbl.getSelectedRow(); if(r>=0) deleteCar(getSelectedCar(r));});
            bottom.add(addBtn); bottom.add(editBtn); bottom.add(delBtn); add(bottom,BorderLayout.SOUTH);
        }
        public void setCurrentUser(User u){ this.currentUser = u; refresh(); }
        private Car getSelectedCar(int row){ String id=(String)model.getValueAt(row,0); return CarController.findCarById(id); }
        public void refresh(){
            model.setRowCount(0);
            List<Car> cars = CarController.fetchCars();
            for(Car c : cars){
                if(currentUser!=null && currentUser.role.equals("seller") && !c.ownerId.equals(currentUser.id)) continue;
                model.addRow(new Object[]{c.id,c.name,c.model,c.pricePerDay,c.status,c.fuelType,c.seats,c.transmission});
            }
        }
        private void addOrEditCar(Car car){
            if(car!=null && currentUser!=null && currentUser.role.equals("seller") && !car.ownerId.equals(currentUser.id)){ JOptionPane.showMessageDialog(this,"You cannot edit this car.","Permission Denied",JOptionPane.WARNING_MESSAGE); return; }
            JTextField name = new JTextField(car!=null?car.name:""); JTextField modelField = new JTextField(car!=null?car.model:""); JTextField price = new JTextField(car!=null?""+car.pricePerDay:""); JTextField fuel = new JTextField(car!=null?car.fuelType:""); JTextField seats = new JTextField(car!=null?""+car.seats:""); JTextField trans = new JTextField(car!=null?car.transmission:""); JTextField category = new JTextField(car!=null?car.category:""); JComboBox<String> statusBox = new JComboBox<>(new String[]{"Available","Rented"}); if(car!=null) statusBox.setSelectedItem(car.status);
            JButton imgBtn = new JButton("Choose Image"); JLabel imgLbl = new JLabel(car!=null?car.imagePath:"");
            imgBtn.addActionListener(e->{ JFileChooser fc = new JFileChooser(); fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files","jpg","jpeg","png")); int res = fc.showOpenDialog(this); if(res==JFileChooser.APPROVE_OPTION) imgLbl.setText(fc.getSelectedFile().getAbsolutePath()); });
            JPanel panel = new JPanel(new GridLayout(0,2,6,6)); panel.add(new JLabel("Name:")); panel.add(name); panel.add(new JLabel("Model:")); panel.add(modelField); panel.add(new JLabel("Price/Day:")); panel.add(price); panel.add(new JLabel("Fuel:")); panel.add(fuel); panel.add(new JLabel("Seats:")); panel.add(seats); panel.add(new JLabel("Transmission:")); panel.add(trans); panel.add(new JLabel("Category:")); panel.add(category); panel.add(new JLabel("Status:")); panel.add(statusBox); panel.add(new JLabel("Image:")); panel.add(imgBtn);
            int ans = JOptionPane.showConfirmDialog(this,panel,car==null?"Add Car":"Edit Car",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
            if(ans==JOptionPane.OK_OPTION){
                try{
                    String newImg = imgLbl.getText().isEmpty()? (car!=null?car.imagePath:PLACEHOLDER) : UIUtils.copyImageToStore(new File(imgLbl.getText()), UUID.randomUUID().toString()+".png");
                    Car newCar = new Car(car!=null?car.id:"C"+System.currentTimeMillis()%100000, name.getText().trim(), modelField.getText().trim(), Double.parseDouble(price.getText().trim()), category.getText().trim(), (String)statusBox.getSelectedItem(), newImg, (currentUser!=null && currentUser.role.equals("seller"))?currentUser.id:(car!=null?car.ownerId:(currentUser!=null?currentUser.id:"U001")), fuel.getText().trim(), Integer.parseInt(seats.getText().trim()), trans.getText().trim());
                    if(car==null) CarController.addCar(newCar); else CarController.updateCar(newCar);
                    refresh();
                }catch(Exception e){ JOptionPane.showMessageDialog(this,"Invalid input!","Error",JOptionPane.ERROR_MESSAGE); }
            }
        }
        private void deleteCar(Car car){
            if(car!=null && currentUser!=null && currentUser.role.equals("seller") && !car.ownerId.equals(currentUser.id)){ JOptionPane.showMessageDialog(this,"You cannot edit this car.","Permission Denied",JOptionPane.WARNING_MESSAGE); return; }
            int ans = JOptionPane.showConfirmDialog(this,"Delete car "+car.name+"?","Confirm",JOptionPane.YES_NO_OPTION);
            if(ans==JOptionPane.YES_OPTION){ CarController.deleteCar(car.id); refresh(); }
        }
    }

    // ---------------- Admin Panel ----------------
    class AdminPanel extends JPanel {
        private JTable tbl; private DefaultTableModel model;
        public AdminPanel(MainFrame parent){
            setLayout(new BorderLayout()); setBackground(BACKGROUND_LIGHT); add(UIUtils.makeHeader("Registered Users"),BorderLayout.NORTH);
            model = new DefaultTableModel(new String[]{"ID","Username","Role","Contact"},0){ public boolean isCellEditable(int r,int c){return false;} };
            tbl = new JTable(model); tbl.getTableHeader().setBackground(NAVY_HEADER); tbl.getTableHeader().setForeground(Color.WHITE); tbl.setRowHeight(25);
            add(new JScrollPane(tbl),BorderLayout.CENTER);
        }
        public void refresh(){ model.setRowCount(0); for(User u: UserController.fetchUsers()) model.addRow(new Object[]{u.id,u.username,u.role,u.contact}); }
    }

    // ---------------- Booking History Panel ----------------
    class BookingHistoryPanel extends JPanel {
        private JTable tbl; private DefaultTableModel model; private User currentUser;
        public BookingHistoryPanel(MainFrame parent){
            setLayout(new BorderLayout()); setBackground(BACKGROUND_LIGHT); add(UIUtils.makeHeader("My Booking History"),BorderLayout.NORTH);
            model = new DefaultTableModel(new String[]{"Booking ID","Car","Days","Status","Pickup Place","Date","Time","Total Price (₹)"},0){ public boolean isCellEditable(int r,int c){return false;} };
            tbl = new JTable(model); tbl.getTableHeader().setBackground(NAVY_HEADER); tbl.getTableHeader().setForeground(Color.WHITE); tbl.setRowHeight(25);
            add(new JScrollPane(tbl),BorderLayout.CENTER);
        }
        public void setCurrentUser(User u){ this.currentUser=u; refresh(); }
        public void refresh(){
            model.setRowCount(0);
            if(currentUser==null) return;
            List<Booking> bookings = BookingController.fetchBookingsByUserId(currentUser.id);
            for(Booking b: bookings){
                Car car = CarController.findCarById(b.carId);
                String carName = car!=null ? car.name + " (" + car.model + ")" : "Unknown Car";
                model.addRow(new Object[]{b.id, carName, b.days, b.status, b.pickupPlace, b.pickupDate, b.pickupTime, "₹"+MONEY.format(b.totalPrice)});
            }
        }
    }

    // ---------------- All Bookings Panel ----------------
    class AllBookingsPanel extends JPanel {
        private JTable tbl; private DefaultTableModel model; private User currentUser; private int selectedRow=-1;
        private JButton btnApprove; private JButton btnReject;
        public AllBookingsPanel(MainFrame parent){
            setLayout(new BorderLayout()); setBackground(BACKGROUND_LIGHT);
            model = new DefaultTableModel(); tbl = new JTable(model); tbl.getTableHeader().setBackground(NAVY_HEADER); tbl.getTableHeader().setForeground(Color.WHITE); tbl.setRowHeight(25);
            btnApprove = new JButton("Approve"); btnApprove.setBackground(ACCENT_GREEN); btnApprove.setForeground(Color.WHITE);
            btnReject = new JButton("Reject"); btnReject.setBackground(ACCENT_RED); btnReject.setForeground(Color.WHITE);
            btnApprove.addActionListener(e->handleBookingAction("Confirmed")); btnReject.addActionListener(e->handleBookingAction("Rejected"));
            JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); controlPanel.setBackground(BACKGROUND_LIGHT); controlPanel.add(btnApprove); controlPanel.add(btnReject);
            tbl.getSelectionModel().addListSelectionListener(e->{ if(!e.getValueIsAdjusting() && tbl.getSelectedRow()!=-1){ selectedRow = tbl.convertRowIndexToModel(tbl.getSelectedRow()); updateControlButtons(); } });
            add(controlPanel, BorderLayout.SOUTH); add(new JScrollPane(tbl), BorderLayout.CENTER);
        }
        public void setCurrentUser(User u){ this.currentUser=u; refresh(); }
        private void updateControlButtons(){
            if(selectedRow>=0 && model.getColumnCount()>2){
                Object statusValue = model.getValueAt(selectedRow,2); boolean isPending = "Pending".equals(statusValue);
                if(currentUser.role.equals("seller")){ btnApprove.setVisible(true); btnReject.setVisible(true); btnApprove.setEnabled(isPending); btnReject.setEnabled(isPending); }
                else { btnApprove.setVisible(false); btnReject.setVisible(false); }
            } else { btnApprove.setEnabled(false); btnReject.setEnabled(false); if(currentUser!=null && !currentUser.role.equals("admin")){ btnApprove.setVisible(false); btnReject.setVisible(false); } }
        }
        private void handleBookingAction(String action){
            if(selectedRow<0) return;
            String bookingId = (String) model.getValueAt(selectedRow,0);
            String carId = (String) model.getValueAt(selectedRow,1);
            BookingController.updateBookingStatus(bookingId, action);
            if(action.equals("Confirmed")){
                Car c = CarController.findCarById(carId); if(c!=null){ c.status="Rented"; CarController.updateCar(c); }
            } else if(action.equals("Rejected")){
                Car c = CarController.findCarById(carId); if(c!=null && c.status.equals("Rented")){ c.status="Available"; CarController.updateCar(c); }
            }
            JOptionPane.showMessageDialog(this, "Booking " + bookingId + " has been " + action.toLowerCase() + ".", "Action Complete", JOptionPane.INFORMATION_MESSAGE);
            refreshAll();
        }
        public void refresh(){
            if(currentUser==null) return;
            String[] headers = currentUser.role.equals("admin")
                    ? new String[]{"Booking ID","Car ID","Status","Buyer","Owner Contact","Date","Time","Days","Total Price (₹)"}
                    : new String[]{"Booking ID","Car ID","Status","Buyer","Buyer Contact","Date","Time","Days","Total Price (₹)"};
            model = new DefaultTableModel(headers,0); tbl.setModel(model);
            List<Booking> all = BookingController.fetchBookings();
            for(Booking b: all){
                Car car = CarController.findCarById(b.carId);
                User buyer = UserController.fetchUsers().stream().filter(u->u.id.equals(b.userId)).findFirst().orElse(null);
                User owner = car!=null ? UserController.fetchUsers().stream().filter(u->u.id.equals(car.ownerId)).findFirst().orElse(null) : null;
                if(currentUser.role.equals("seller") && (car==null || !car.ownerId.equals(currentUser.id))) continue;
                if(currentUser.role.equals("admin")){
                    model.addRow(new Object[]{b.id, car!=null?car.id:"N/A", b.status, buyer!=null?buyer.username:"Unknown", owner!=null?owner.contact:"N/A", b.pickupDate, b.pickupTime, b.days, "₹"+MONEY.format(b.totalPrice)});
                } else {
                    model.addRow(new Object[]{b.id, car!=null?car.id:"N/A", b.status, buyer!=null?buyer.username:"Unknown", buyer!=null?buyer.contact:"N/A", b.pickupDate, b.pickupTime, b.days, "₹"+MONEY.format(b.totalPrice)});
                }
            }
            updateControlButtons();
            if(tbl.getColumnModel().getColumnCount()>2) tbl.getColumnModel().getColumn(2).setCellRenderer(new StatusCellRenderer());
            model.fireTableDataChanged();
            revalidate(); repaint();
        }
    }

    // ---------------- StatusCellRenderer ----------------
    class StatusCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if(value!=null){
                String status = value.toString();
                if(status.equals("Confirmed")){ c.setForeground(ACCENT_GREEN); c.setFont(c.getFont().deriveFont(Font.BOLD)); }
                else if(status.equals("Pending")){ c.setForeground(ACCENT_ORANGE); c.setFont(c.getFont().deriveFont(Font.BOLD)); }
                else if(status.equals("Rejected")){ c.setForeground(ACCENT_RED); c.setFont(c.getFont().deriveFont(Font.BOLD)); }
                else { c.setForeground(Color.BLACK); c.setFont(c.getFont().deriveFont(Font.PLAIN)); }
            }
            return c;
        }
    }

    // ---------------- UIUtils (small helper) ----------------
    static class UIUtils {
        public static final DecimalFormat MONEY = new DecimalFormat("#,###.##");
        public static final String PLACEHOLDER_IMAGE = PLACEHOLDER;
        public static void ensureImagesFolder(){ try{ Files.createDirectories(Paths.get(IMAGE_FOLDER)); } catch(Exception ignored){} }
        public static String copyImageToStore(File sourceFile,String targetFileName) throws java.io.IOException {
            ensureImagesFolder();
            Path target = Paths.get(IMAGE_FOLDER, targetFileName);
            java.nio.file.Files.copy(sourceFile.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString().replace("\\","/");
        }
        public static ImageIcon loadScaledIcon(String imagePath,int w,int h){
            if(imagePath==null || !(new File(imagePath)).exists()) imagePath = PLACEHOLDER_IMAGE;
            try {
                BufferedImage img = ImageIO.read(new File(imagePath));
                return new ImageIcon(img.getScaledInstance(w,h,Image.SCALE_SMOOTH));
            } catch (Exception e){ return null; }
        }
        public static JLabel makeHeader(String text){
            JLabel l = new JLabel(text, SwingConstants.CENTER);
            l.setFont(new Font("Segoe UI", Font.BOLD, 20));
            l.setOpaque(true); l.setBackground(NAVY_HEADER); l.setForeground(Color.WHITE); l.setBorder(new EmptyBorder(12,12,12,12));
            return l;
        }
    }

    // ---------------- WrapLayout (from original) ----------------
    class WrapLayout extends FlowLayout {
        public WrapLayout(){ super(); }
        public WrapLayout(int align,int hgap,int vgap){ super(align,hgap,vgap); }
        public Dimension preferredLayoutSize(Container target){ return layoutSize(target,true); }
        public Dimension minimumLayoutSize(Container target){ return layoutSize(target,false); }
        private Dimension layoutSize(Container target, boolean preferred){
            synchronized(target.getTreeLock()){
                int w = target.getWidth(); if(w==0) w=Integer.MAX_VALUE;
                Insets insets = target.getInsets(); int maxWidth = w - insets.left - insets.right - 10;
                int x = 0, y = insets.top, rowHeight = 0; int hgap = getHgap(); int vgap = getVgap();
                for(Component c : target.getComponents()){
                    if(!c.isVisible()) continue;
                    Dimension d = preferred ? c.getPreferredSize() : c.getMinimumSize();
                    if(x + d.width > maxWidth){ x = 0; y += rowHeight + vgap; rowHeight = 0; }
                    x += d.width + hgap; rowHeight = Math.max(rowHeight, d.height);
                }
                y += rowHeight + insets.bottom;
                return new Dimension(w, y);
            }
        }
    }

    // ---------------- main ----------------
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            try { UIUtils.ensureImagesFolder(); new MainFrame().setVisible(true); } catch(Exception e){ e.printStackTrace(); }
        });
    }
}
