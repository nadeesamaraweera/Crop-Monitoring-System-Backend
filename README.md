# 🌱 **Green Shadow Management System - Backend** 🌾
_A Spring Boot-powered backend system for efficient farm management._

---
## 🎯 **Features**
✨ **Field Management**: CRUD operations with GPS integration.  
🌾 **Crop Management**: Manage crop types, growth stages, and seasonal data.  
👥 **Staff Management**: Assign roles and allocate staff to fields and vehicles.  
📋 **Monitoring Logs**: Track crop observations and activities.  
🚜 **Vehicle & Equipment Management**: Oversee allocation and maintenance status.  
🔐 **Authentication & Authorization**: Role-based access with JWT.  
📊 **Data Analysis**: Perform relational, spatial, and temporal analysis.  
✅ **Validation**: Comprehensive validation at all levels.  
⚠️ **Custom Exception Handling**: Informative error messages for better debugging.
---
## 🛠️ **Tech Stack**
### 🖥️ **Backend Frameworks & Libraries**
- **🌟 Spring Boot**: Core framework.
- **🔒 Spring Security**: Authentication & Authorization (JWT & OAuth 2.0).
- **📂 Spring Data JPA**: Simplified database interactions.
- **🛠️ Lombok**: Reduces boilerplate in Java.
- **🔄 Model Mapper**: Seamless mapping between DTOs and Entities.
- **📦 Jackson**: JSON processing.

### 🗄️ **Database**
- **🐬 MySQL**: Relational database management system.
---
## 🚀 **Getting Started**

### **Prerequisites**
- 📌 **Java 17** or higher
- 📌 **MySQL Server** installed and running
- 📌 **Gradle** for dependency management

---
## 🏗️ **Key Modules**

- **🔐 Auth Service**:Handles user authentication, including login functionality and role-based access control using JWT.  
- **🗺️ Field Service**:Manages field details, including GPS location, field area, and crop allocations. Supports CRUD operations for fields.  
- **🌿 Crop Service**:Provides CRUD operations for managing crops, including data about crop categories, types, growth stages, and seasons.  
- **👥 Staff Service**:Manages staff details, including roles, assignments to fields or vehicles, and staff responsibilities.  
- **📋 Monitoring Log Service**:Tracks and records observations, activities, and data logs related to crop and field management.  
- **🚜 Vehicle & Equipment Service**:Manages vehicles and equipment used in the farm operations, including maintenance status, allocation, and usage tracking.
## 🔒 **Security**
- 🔑 **JWT Authentication**: Ensures secure user sessions by using JSON Web Tokens for user authentication.
- 🎭 **Role-Based Access Control (RBAC)**: Assigns specific permissions based on user roles like MANAGER, ADMINISTRATIVE, and SCIENTIST.
- 🔐 **Password Encryption**: User passwords are securely encrypted using BCrypt to ensure data protection.
## 📈 **Future Enhancements**

- 🌤️ **Weather API Integration**: Incorporate weather data to improve crop monitoring and decision-making.
- 📊 **Advanced Analytics Dashboards**: Develop dynamic dashboards for detailed insights into field and crop performance.
- 📱 **Mobile App Integration**: Enable on-the-go updates for field data via a mobile application, improving real-time field management.
---
### **Clone the Repository**
git clone <https://github.com/nadeesamaraweera/Crop-Monitoring-System-Backend.git>

----
## 🔗 **API Endpoints**

| HTTP Method | Endpoint               | Description                | Role Access               |
|-------------|------------------------|----------------------------|---------------------------|
| POST        | /api/auth/signIn       | Login to the system        | All Roles                 |
| POST        | /api/field             | Add a new field            | MANAGER                   |
| GET         | /api/field             | Retrieve all fields        | All Roles                 |
| PUT         | /api/field/{fieldCode} | Update field details       | MANAGER                   |
| DELETE      | /api/field/{fieldCode} | Delete a field             | MANAGER                   |
| POST        | /api/crop              | Add a new crop             | MANAGER, SCIENTIST        |
| GET         | /api/crop              | Retrieve all crops         | All Roles                 |
| POST        | /api/staff             | Add new staff              | MANAGER, ADMINISTRATIVE   |
| GET         | /api/staff             | Retrieve all staff         | All Roles                 |

📖 **Full API documentation** is available in the project folder.


---
## 📜 **License**

This project is licensed under the [MIT License](https://github.com/nadeesamaraweera/Crop-Monitoring-System-Backend/MIT).