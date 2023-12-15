# Project description
This is a Google Drive-like filehosting solution for local area networks to share files across several computers.

# Installation Guide
## Frontend
1. Download and install Node.
2. Run the command `npm install` in `/landrive/dashboard/`.

## Backend
1. Download and install Gradle.
2. Run `gradle installDist` in the `/landrive/`.
3. Open a new terminal and run `runwebserver.bat` to start the webserver.
4. Open a new terminal and run `runfileserver.bat` to start a file server.
5. Run `connect` in the file server to connect it to the webserver.

## Web browser
When all services are up you go to <http://localhost:8080/dashboard> to view the webpage.
