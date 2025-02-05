ExpenseTracker 🏦💰
A simple Java-based Expense Tracker application using Swing for the GUI and SQLite for database storage. This tool helps users manage and track their daily expenses efficiently.

📌 Features
✅ User Authentication (Signup & Login)
✅ Add Expenses with Category, Amount, Date & Notes
✅ View Expenses in a structured format
✅ Dark Mode & Light Mode Toggle 🌙☀️
✅ Data stored securely in SQLite Database

🚀 Installation & Setup
1️⃣ Clone the Repository
sh
Copy
Edit
git clone https://github.com/Yandisa619/ExpenseTracker.git
cd ExpenseTracker
2️⃣ Open the Project in IntelliJ IDEA
Go to File > Open and select the project folder.
Make sure JDK 17+ is installed and set up.
Install SQLite JDBC Driver if needed.
3️⃣ Run the Application
Open gui.LoginForm in IntelliJ IDEA.
Click Run ▶ to start the application.
🛠️ Tech Stack
Java (Swing) – GUI Development
SQLite – Database Management
Git & GitHub – Version Control
📂 Project Structure
bash
Copy
Edit
ExpenseTracker/
│── src/
│   ├── gui/               # User Interface (Swing Forms)
│   │   ├── LoginForm.java
│   │   ├── SignupForm.java
│   │   ├── Dashboard.java
│   │   ├── ExpenseForm.java
│   │
│   ├── database/          # Database Handling
│   │   ├── DatabaseHelper.java
│
│── assets/                # Icons & Images
│── README.md              # Project Documentation
│── expenses.db            # SQLite Database File
│── .gitignore             # Git Ignore Rules
📌 To-Do (Future Enhancements)
🔲 Add Expense Editing & Deleting
🔲 Generate Expense Reports & Charts 📊
🔲 Export Data to CSV/PDF

📜 License
This project is open-source under the MIT License. Feel free to contribute! 🤝
