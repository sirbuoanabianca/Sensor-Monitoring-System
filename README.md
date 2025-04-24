## Sensor Monitoring System 

The project involves an online platform implemented to manage users, their associated devices and the data monitored from each device. The system can be accessed by two types of users after a login process: administrator and customers.

The administrator can perform CRUD (Create-Read-Update-Delete) operations on user accounts (defined by ID, name, role), registered smart energy metering devices (defined by ID, description, address, maximum energy consumption per hour) and on user-device mapping (each user can own one or more smart devices in different locations).
Later, the message broker feature was added that collects data from devices, preprocesses the data to calculate daily hourly consumption and stores it in the database. The message broker will actually be a simulator that reads sensor values ​​from a specific preset device at a certain time period and sends them to the queue in JSON format.


Technologies used:

• Backend: REST services -> Java Spring REST, RabbitMQ (CloudAmpq queue), WebSockets.

• Frontend: ReactJS

• Database used: PostgreSQL



![Screenshot 2025-04-24 132154](https://github.com/user-attachments/assets/839267ad-11b9-4c3c-8f59-ba6c08acd8c2)
