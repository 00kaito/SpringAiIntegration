# Task: TASK-B94A1B

## Title: ### **Task KNOWLEDGE-1.4: Create Health Check Endpoint**

### **Task KNOWLEDGE-1.4: Create Health Check Endpoint**
**Story Points:** 2  
**Priority:** High  
**Assignee:** Backend Developer  
**Sprint:** 1

#### **Business Context**
Operations team needs to monitor system availability. Health check endpoint allows load balancers and monitoring tools to verify the application is running and dependencies (database, Redis) are accessible.

#### **Technical Specifications**
- Create HealthController.java at /api/health
- Create HealthService.java with business logic
- Implement GET /api/health endpoint returning:
  `json
  {
     status: OK,
    timestamp: 2026-06-01T13:00:00Z,
    components: {
      database: UP,
      redis: UP
    }
  }
  `
- Add database connectivity check
- Add Redis connectivity check
- Use Spring Boot Actuator for health indicators

#### **Acceptance Criteria**
- [ ] GET /api/health returns HTTP 200
- [ ] Response contains status, timestamp, and components
- [ ] Database check returns UP when connected
- [ ] Redis check returns UP when connected
- [ ] Returns DOWN status when component unavailable
