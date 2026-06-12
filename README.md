# java-l7-load-balancer

# Custom Layer 7 Load Balancer & Reverse Proxy

A high-performance, multi-threaded Layer 7 (Application Layer) load balancer and reverse proxy built completely from scratch in Java. This system acts as an intelligent traffic gateway, sitting in front of multiple backend application servers to distribute incoming HTTP traffic efficiently, ensure high availability, and prevent server overload.

---

## 🚀 Key Features

* **Layer 7 Path-Based Routing:** Parses incoming raw HTTP/1.1 request strings over TCP to route traffic dynamically based on URL paths.
* **Multi-Threaded Architecture:** Utilizes a thread pool (`ExecutorService`) to handle hundreds of concurrent client connections simultaneously without blocking.
* **Round-Robin Load Balancing:** Implements a thread-safe atomic routing algorithm (`AtomicInteger`) to cycle traffic evenly across active backend nodes.
* **Active Backend Health Checks:** Runs a background daemon thread that actively pings registered backend servers at regular intervals, dynamically removing crashed instances from the rotation pool.
* **Reverse Proxy Shield:** Disguises backend server topography, safeguarding internal infrastructure from direct internet exposure.

---

## 🏗️ Architectural Overview

```text
                    +----------------------+
                    |       Client         |
                    +----------+-----------+
                               |
                               v
                    +----------------------+
                    |    Load Balancer     |
                    |      Port 8000       |
                    +----------+-----------+
                               |
          +-------------------+-------------------+
          |                   |                   |
          v                   v                   v
 +----------------+ +----------------+ +----------------+
 | Backend Server | | Backend Server | | Backend Server |
 |    Port 9001   | |    Port 9002   | |    Port 9003   |
 +----------------+ +----------------+ +----------------+

            +--------------------------+
            |      Health Checker      |
            |  Active Server Monitoring|
            +--------------------------+

            +--------------------------+
            |         Metrics          |
            | Request Count Per Server |
            +--------------------------+
```
---
## 🛠️ Tech Stack & Core Concepts
**Language:** Java (JDK 17+)

**Networking:** Java Sockets (ServerSocket, Socket)

**Concurrency:** ExecutorService, ThreadPoolExecutor, AtomicInteger, Thread Safety

**Protocols:** HTTP/1.1, TCP/IP

---

## 🎯 Project Status

### Completed Features

- [x] Round Robin Load Balancing
- [x] Reverse Proxy Request Forwarding
- [x] Multi-threaded Request Handling using ExecutorService
- [x] Active Backend Health Checks
- [x] Automatic Failover and Recovery
- [x] HTTP Request Forwarding
- [x] Request Metrics Collection

### Future Improvements

- [ ] Weighted Round Robin Routing
- [ ] Configuration File Support
- [ ] Docker Deployment
- [ ] Monitoring Dashboard
---

## ▶️ How To Run

### 1. Clone the Repository

```bash
git clone https://github.com/MohammadSaum/java-l7-load-balancer.git
cd java-l7-load-balancer
```

### 2. Compile the Project

```bash
javac src/*.java
```

### 3. Start Backend Servers

Open three separate terminals:

**Terminal 1**
```bash
java -cp src BackendServer 9001 Server-1
```

**Terminal 2**
```bash
java -cp src BackendServer 9002 Server-2
```

**Terminal 3**
```bash
java -cp src BackendServer 9003 Server-3
```

### 4. Start the Load Balancer

Open a fourth terminal:

```bash
java -cp src LoadBalancer
```

### 5. Run the Client

Open a fifth terminal:

```bash
java -cp src Client
```

### 6. Verify Round Robin Routing

Run the client multiple times. Requests should be distributed across:

- Server-1 (Port 9001)
- Server-2 (Port 9002)
- Server-3 (Port 9003)

### 7. Test Failover & Recovery

1. Stop one backend server.
2. Wait for the Health Checker to detect the failure.
3. Observe the server being removed from the active pool.
4. Restart the backend server.
5. Observe the server being automatically restored to the active pool.

### 8. Monitor Metrics

The Metrics component periodically displays:

- Requests handled by Server-1
- Requests handled by Server-2
- Requests handled by Server-3

## 📊 Example Features Demonstrated

- Load distribution using Round Robin scheduling
- Concurrent request processing using ExecutorService
- Backend health monitoring
- Automatic failover and recovery
- HTTP request forwarding
- Per-server request metrics collection