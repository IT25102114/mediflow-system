<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Login"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="auth-page">
    <div class="auth-card">
        <div class="auth-header">
            <h1>Welcome Back</h1>
            <p>Login to your MediFlow account</p>
        </div>
        <c:if test="${error != null}"><div class="error-alert">&#x26A0; ${error}</div></c:if>
        <c:if test="${success != null}"><div class="alert alert-success">&#x2705; ${success}</div></c:if>
        <form action="/login" method="post">
            <div class="input-group">
                <span class="input-icon">&#x2709;</span>
                <input type="email" name="email" placeholder="Email address" required>
            </div>
            <div class="input-group">
                <span class="input-icon">&#x1F512;</span>
                <input type="password" name="password" placeholder="Password" required>
            </div>
            <button type="submit" class="btn-primary auth-btn">Login &#x2192;</button>
        </form>
        <div class="auth-footer">Don't have an account? <a href="/register">Register</a></div>
        <div class="demo-credentials">
            <p><strong>Demo Admin:</strong> admin@mediflow.lk / password123</p>
            <p><strong>Demo User:</strong> kasun@gmail.com / password123</p>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
