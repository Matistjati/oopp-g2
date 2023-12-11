# Project description
This is a Google drive like fileserver that you can run over lan on your network to share files across several devices.

# Installation Guide
## Frontend
1. Download Node
2. Run `npm install` in /dashboard

## Backend
1. Download gradle
2. Run `gradle installDist` in the '/landrive' folder
3. Open a new terminal and run `runwebserver.bat` to start the webserver
4. Open a new terminal and run `runfileserver.bat` to start a file server
5. Run `connect` in the file server to connect it to the webserver

## Web browser
When all services are up you go to "localhost:8080/dashboard" to view the webpage.