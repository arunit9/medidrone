# file-process-application

## Application Overview

The Medidrone Application Consists of 2 components
- medidrone: Spring Boot REST services for loading and dispatching drones
- MySQL database

Once the build/run scripts are executed, a docker stack will be running with 2 containers corresponding to the above components

## Building and Running the Application

### Build

#### Prerequisites

The following are required:
- Jdk 1.8 (preferred) or jdk 11
- Maven
- Docker
- docker-compose
- port 8080 and 3306 must be free

If you do not have the above installed, please run below supplementary scripts in the given order. Executable permission need to be given prior to execution.
- sudo su
- chmod +x setup.sh
- chmod +x docker_setup.sh
- chmod +x docker_compose_setup.sh
- ./setup.sh
- ./docker_setup.sh
- ./docker_compose_setup.sh

Please note that the command line will prompt a few questions while running for which you need to type “y” (yes).

Once done, please verify maven, java, docker and docker-compose versions to see if installation had been successful.

#### Project Build

The project can be cloned or downloaded from https://github.com/arunit9/medidrone.git

Open a terminal inside the medidrone directory. The project_build.sh is located here. This file will build docker images and run docker containers for the medidrone (SpringBoot REST API) and the MySQL database.

**NOTE: This script contains docker commands, hence you may have to sudo su**

- chmod +x project_build.sh
- ./project_build.sh

or run,
- mvn package
- docker build -t arunit9/medidrone .
- docker-compose up

Once the execution has completed, you should see 2 docker containers as below.
- app-medidrone at port 8080
- app-mysql at port 3306

The application can be accessed via a web browser at localhost:8080

### Run

The above process can be run, re-run as many times as you like to build and run the application. The project_run.sh is provided as a quicker option to simply just run the application. It will simply start the docker stack using docker images in your local docker (file-process-api and file-process-web) or Docker Hub (app-mysql). Please ensure that you have run the project_build.sh atleast once before running this script.

**NOTE: I have not pushed my docker images to the Docker Hub**


- chmod +x project_run.sh
- ./project_run.sh

or run,
- docker-compose up

### Stop
The project_stop.sh can be executed to stop the application (and the docker stack).

- chmod +x project_stop.sh
- ./project_stop.sh

or run,
- docker-compose down

**NOTE: The file process Spring Boot application is set to delete the content of the file upload directory and drop and recreate the database tables at start.**

## Design
Following decisions were made to the best of my best knowledge considering the requirements of the assignment.

### Load Medication
A loading of medication to a drone is restricted to a one load request at a time. Multiple load requests cannot load the same drone even if there is capacity to load more items. This is done to avoid drones being
left in loading state.

The knapsack algorithm is used to calculate the optimal combination of medication items to be loaded within the weight limit of a given drone. All medication items are considered of equal value (priority), hence the algorithm only considers the weight as a variable. It is assumed that items are not divisble (fractions of items cannot be loaded). Algorithm can be broken down to 2 steps,

- Find the maximum possible weight of different medication item combinations (within the weight limit)
- Find the items which are included/excluded from the package at that weight

Eg: If the drone's weight limit is 500mgr and the total weight of medication items is 400mgr. All items can be loaded.

If the total weight of medication items is 600mgr, the algorithm decides the optimal combination.

X + Y + Z -> 600mgr
X + Y -> 400mgr
Y + Z -> 500mgr
X + Z -> 300 mgr

Optimal load is 500mgr with only Y and Z items loaded.

### Weight
Weight is persisted as an Integer value representing the weight in milli grams (mgr)

### Battery Capacity
Battery Capacity is persisted as an Integer value representing the battery capacity in milli grams (mgr)

### Uploading Medication product details
The details for a given medical product such as the product name, code and image is done by sql injection at application startup. Additionally, REST api methods are available (MedicationController) to register and view products.

## REST API
### Auth
#### Signup
**URL**
http://localhost:8080/api/auth/signup

**Sample Request**
```yaml
{
    "username" : "andrew",
    "password" : "password",
    "role" : ["user", "mod"]
}
```

**Sample Response**
```yaml
{
    "message": "User registered successfully!"
}
```

#### Login
**URL**
http://localhost:8080/api/auth/signin

**Sample Request**
```yaml
{
    "username" : "andrew",
    "password" : "password"
}
```

**Sample Response**
```yaml
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmRyZXciLCJpYXQiOjE2NjQxMzY0MTcsImV4cCI6MTY2NDIyMjgxN30.yIpcEd9LCvLKbEpkHzsdew5deWtWqesRmsSDeZw20u5V_vY1o-JofJgx9N9aGzuNOyi9I45TntC_VJadSj3YLw",
    "type": "Bearer",
    "id": 1,
    "username": "andrew",
    "roles": [
        "ROLE_MODERATOR",
        "ROLE_USER"
    ]
}
```

### Medication Products
This is an optional api to register/view medication products. A set of products will be sql injected at application start up.

#### Register
**URL**
http://localhost:8080/medication/register

**Sample Request**
Request Parameters:
Name: <name_of_medication>
Code: <medication_unique_code>
Image: <image_file>

**Sample Response**
```yaml
{
    "medication": {
        "name": "antibiotic1",
        "weight": null,
        "code": "MED159622",
        "image": "MED159622.png"
    }
    "status": "success"
}
```

#### Get All
**URL**
http://localhost:8080/medication

**Sample Request**
N/A

**Sample Response**
```yaml
{
    "medication": [
        {
            "name": "antibiotic1",
            "weight": null,
            "code": "MED159622",
            "image": "MED159622.png"
        },
        {
            "name": "antibiotic2",
            "weight": null,
            "code": "MED164529",
            "image": "MED164529.png"
        },
        {
            "name": "antibiotic3",
            "weight": null,
            "code": "MED281285",
            "image": "MED281285.png"
        },
        {
            "name": "antibiotic4",
            "weight": null,
            "code": "MED281536",
            "image": "MED281536.png"
        },
        {
            "name": "antibiotic5",
            "weight": null,
            "code": "MED281754",
            "image": "MED281754.png"
        },
        {
            "name": "antibiotic7",
            "weight": null,
            "code": "MED3443262",
            "image": "MED3443262.png"
        },
        {
            "name": "antibiotic7",
            "weight": null,
            "code": "MED4377029",
            "image": "MED4377029.png"
        },
        {
            "name": "antibiotic6",
            "weight": null,
            "code": "MED546193",
            "image": "MED546193.png"
        }
    ]
}
```

#### Get One
**URL**
http://localhost:8080/medication/MED159622

**Sample Request**
N/A

**Sample Response**
```yaml
{
    "medication": {
        "name": "antibiotic1",
        "weight": null,
        "code": "MED159622",
        "image": "MED159622.png"
    }
}
```

#### Get image
**URL**
http://localhost:8080/medication/image/MED159622.png

**Sample Request**
N/A

**Sample Response**
Image downloaded

### Drone Dispatcher

#### Register Drone
**URL**
http://localhost:8080/dispatcher/drone/register

**Sample Request**

```yaml
{
        "drone": {
            "serialNumber":"SN000000001",
            "model":"Lightweight",
            "weightLimit":400,
            "batteryCapacity":75
        }

}
```
**Sample Response**
```yaml
{
    "drone": {
        "serialNumber": "SN000000001",
        "model": "Lightweight",
        "weightLimit": 400,
        "batteryCapacity": 75,
        "state": "IDLE"
    },
    "status": "Success"
}
```

#### Find available drones
**URL**
http://localhost:8080/dispatcher/drone

**Sample Request**
N/A

**Sample Response**
```yaml
{
    "drones": [
        {
            "serialNumber": "SN000000001",
            "model": "Lightweight",
            "weightLimit": 400,
            "batteryCapacity": 75,
            "state": "IDLE"
        },
        {
            "serialNumber": "SN000000002",
            "model": "Lightweight",
            "weightLimit": 400,
            "batteryCapacity": 75,
            "state": "IDLE"
        }
    ]
}
```

#### Load medication to a drone
**URL**
http://localhost:8080/dispatcher/drone/load/SN000000001

**Sample Request**
```yaml
{
    "medication":[
        {
            "name":"antibiotic1",
            "code":"MED159622",
            "weight":300
        },
        {
            "name":"antibiotic2",
            "code":"MED164529",
            "weight":100
        },
        {
            "name":"antibiotic3",
            "code":"MED281285",
            "weight":100
        }
    ]
}
```

**Sample Response**
```yaml
{
    "serialNumber": "SN000000001",
    "loadedMedication": [
        {
            "name": "antibiotic1",
            "weight": 300,
            "code": "MED159622",
            "image": null
        },
        {
            "name": "antibiotic2",
            "weight": 100,
            "code": "MED164529",
            "image": null
        }
    ],
    "remainingMedication": [
        {
            "name": "antibiotic3",
            "weight": 100,
            "code": "MED281285",
            "image": null
        }
    ],
    "status": "Success"
}
```

#### Get loaded medication
**URL**
http://localhost:8080/dispatcher/drone/medication/SN000000001

**Sample Request**
N/A

**Sample Response**
```yaml
{
    "serialNumber": "SN000000001",
    "loadedMedication": [
        {
            "name": "antibiotic1",
            "weight": 300,
            "code": "MED159622",
            "image": "MED159622.png"
        },
        {
            "name": "antibiotic2",
            "weight": 100,
            "code": "MED164529",
            "image": "MED164529.png"
        }
    ]
}
```
#### Check battery capacity of a drone
**URL**
http://localhost:8080/dispatcher/drone/battery/SN000000001

**Sample Request**
N/A

**Sample Response**
{
    "serialNumber": "SN000000001",
    "battery": 75
}