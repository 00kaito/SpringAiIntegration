# Task: TASK-8C5179

## Title: ### **Task KNOWLEDGE-1.2: Setup Docker Compose for Local Development**

### **Task KNOWLEDGE-1.2: Setup Docker Compose for Local Development**
**Story Points:** 2  
**Priority:** Highest  
**Assignee:** Backend Developer  
**Sprint:** Sprint 1

#### **Business Context**
Developers need a consistent local development environment. Docker Compose will provide PostgreSQL database and Redis cache without requiring manual installation, reducing onboarding time from hours to minutes.

#### **Technical Specifications**
- Create docker-compose.yml with:
  - **PostgreSQL service:**
    - Image: pgvector/pgvector:pg17
    - Environment: POSTGRES_DB=knowledgeassist, POSTGRES_USER=postgres, POSTGRES_PASSWORD=postgres
    - Port: 5432:5432
    - Volume: postgres-data:/var/lib/postgresql/data
  - **Redis service:**
    - Image: redis:7-alpine
    - Port: 6379:6379
- Create docker-compose.override.yml for development-specific settings
- Add volumes for data persistence
- Document Docker commands in README

#### **Acceptance Criteria**
- [ ] docker-compose up -d starts all services successfully
- [ ] PostgreSQL accessible at localhost:5432
- [ ] Redis accessible at localhost:6379
- [ ] docker-compose down stops and removes containers
- [ ] Data persists after container restart (volumes working)
