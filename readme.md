
# **College Management System**

This project is a **College Management System** designed to manage and display various student and faculty-related data such as attendance, fees, materials, and academic performance. The system includes dashboards for students, faculty, and admins, along with features like report generation and CSV uploads.

---

## **Features**

### **Admin Features**
- Upload and view attendance, fees, and results.
- Generate and view detailed system reports.
- Upload study materials for students by branch and year.

### **Faculty Features**
- Upload attendance and results for students.
- Share study materials for specific branches and years.
- View uploaded materials and modify them if necessary.

### **Student Features**
- View individual attendance, fee details, and study materials.
- Access academic performance details like CGPA and results.

---

## **Technologies Used**
- **Frontend:**
  - HTML, CSS, Bootstrap 5
  - JavaScript
- **Backend:**
  - Java Spring Boot
  - Hibernate for ORM
- **Database:**
  - MySQL
- **Visualization:**
  - Chart.js for interactive charts

---

## **Setup Instructions**

### **1. Prerequisites**
- Install Java JDK (version 8+).
- Install MySQL and create a database for the project.
- Install an IDE like IntelliJ IDEA or Eclipse.
- Install Maven.

### **2. Clone the Repository**
```bash
git clone <repository_url>
cd college-management-system
```

### **3. Configure the Database**
Update the `application.properties` file in `src/main/resources`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/college_management
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### **4. Run the Application**
Use Maven to build and run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

---

## **Sample CSV Files**

### **1. Attendance CSV**
| RollNo       | Date       | Present |
|--------------|------------|---------|
| 10211A0478   | 2024-11-20 | TRUE    |
| 10211A0482   | 2024-11-20 | FALSE   |

**Format:**
```csv
RollNo,Date,Present
10211A0478,2024-11-20,TRUE
10211A0482,2024-11-20,FALSE
```

### **2. Fees CSV**
| RollNo       | Total Fees | Paid Fees | Unpaid Fees |
|--------------|------------|-----------|-------------|
| 10211A0478   | 50000      | 25000     | 25000       |
| 10211A0482   | 60000      | 60000     | 0           |

**Format:**
```csv
RollNo,Total Fees,Paid Fees,Unpaid Fees
10211A0478,50000,25000,25000
10211A0482,60000,60000,0
```

### **3. Results CSV**
| RollNo       | Subject      | Marks | Semester | Year |
|--------------|--------------|-------|----------|------|
| 10211A0478   | Mathematics  | 85    | 1        | 2024 |
| 10211A0482   | Physics      | 78    | 1        | 2024 |

**Format:**
```csv
RollNo,Subject,Marks,Semester,Year
10211A0478,Mathematics,85,1,2024
10211A0482,Physics,78,1,2024
```

### **4. Study Materials CSV**
| Material Name   | Branch | Year | Google Drive Link                       |
|------------------|--------|------|-----------------------------------------|
| Data Structures | CSE    | 2    | https://drive.google.com/example-link   |
| Circuits        | EEE    | 1    | https://drive.google.com/example-link   |

**Format:**
```csv
Material Name,Branch,Year,Google Drive Link
Data Structures,CSE,2,https://drive.google.com/example-link
Circuits,EEE,1,https://drive.google.com/example-link
```

---

## **Endpoints**

### **Admin**
- **Upload Attendance:** `POST /api/attendance/upload`
- **Upload Fees:** `POST /api/fees/upload`
- **Upload Results:** `POST /api/results/upload`
- **Generate Reports:**
  - Attendance: `GET /api/reports/attendance-report`
  - Fees: `GET /api/reports/fees-report`

### **Faculty**
- **Upload Study Material:** `POST /api/materials`
- **View Uploaded Materials:** `GET /api/materials/my-materials`

### **Student**
- **View Attendance:** `GET /api/attendance/student`
- **View Fee Details:** `GET /api/fees/student-fee`
- **View Results:** `GET /api/results/student`
- **View Study Materials:** `GET /api/materials/student-materials`

---


## **Future Enhancements**
- **Role-Based Authentication:** Add Spring Security to implement role-based access for admin, faculty, and students.
- **File Export:** Allow CSV export of reports for attendance, fees, and results.
- **Notifications:** Add email notifications for fee payment reminders or low attendance warnings.
- **Mobile-Friendly UI:** Make dashboards responsive for mobile devices.

---

Feel free to contribute to this project! 😊
