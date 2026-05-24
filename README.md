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
    [ Client Requests ]
            │
            ▼
┌───────────────────────────┐
│  Layer 7 Load Balancer    │ (Port: 8080)
│  ├─ Thread Pool Handler   │
│  ├─ HTTP Request Parser   │
│  └─ Active Health Checker │ ──(Periodic Heartbeats)──┐
└─────────────┬─────────────┘                         │
              │                                       ▼
      [ Round-Robin Loop ]                 ┌─────────────────────┐
              │                            │ Crashed Server (502)│
     ┌────────┼────────┐                   │  [Temporarily Out]  │
     │        │        │                   └─────────────────────┘
     ▼        ▼        ▼
┌────────┐┌────────┐┌────────┐
│ServerA ││ServerB ││ServerC │
│ (5001) ││ (5002) ││ (5003) │
└────────┘└────────┘└────────┘
```
---
## 🛠️ Tech Stack & Core Concepts
**Language:** Java (JDK 17+)

**Networking:** Java Sockets (ServerSocket, Socket)

**Concurrency:** ExecutorService, ThreadPoolExecutor, AtomicInteger, Thread Safety

**Protocols:** HTTP/1.1, TCP/IP

---

## 🎯 Current Project Status: In Progress
**Development Roadmap**

[x] Repository setup & Architecture Design

[ ] Implement single-threaded TCP Socket Reverse Proxy

[ ] Integrate Java Concurrency Tools for Multi-threaded Request Handling

[ ] Build Layer 7 HTTP parsing & Round-Robin routing logic

[ ] Add active background Health Check daemon

[ ] Performance testing and benchmarking (using Apache Bench / Postman)

---

## To compile the project ##

javac Main.java

## To run the load balancer gateway ##

java Main
