# 🌱 **Green Shadow Management System - Backend** 🌾
_A Spring Boot-powered backend system for efficient farm management._<br>

This backend system is designed to help manage key aspects of farm operations, including field management, crop tracking, staff records, and more. It utilizes Spring Boot for a robust, scalable architecture, allowing easy integration with the frontend and providing seamless management of farm resources across various roles and access levels.

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
- 📌 **Postman** To test and explore the APIs.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

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
# 🔗 **API Endpoints**

Base URL: `http://localhost:8080/greenShadow/`

End Points : **POST | GET | PATCH | DELETE**

Example :
- **POST**: `/api/auth/signIn` -> Login to the system
- **POST**: `/api/field` -> Add a new field
- **GET**: `/api/field` -> Retrieve all fields
- **GET**: `/api/field/{fieldCode}` -> Retrieve a specific field
- **PATCH**: `/api/field/{fieldCode}` -> Update field details
- **DELETE**: `/api/field/{fieldCode}` -> Delete a field  

 ## 📖 **API Documentation**
 To test or explore the API, you can use tools like Postman. Detailed documentation is available at:
<br>
 [API Documentation](https://documenter.getpostman.com/view/35385577/2sAYBa88t2)

---
## 📜 **License**

This project is licensed under the MIT License.See the [LICENSE](./LICENSE) file for details.

----

Copyright © 2024 Nadeesha Samaraweera