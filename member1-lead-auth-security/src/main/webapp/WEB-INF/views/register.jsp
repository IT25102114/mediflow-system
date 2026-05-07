<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Register"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="auth-page">
    <div class="auth-card">
        <div class="auth-header">
            <h1>Create Account</h1>
            <p>Join MediFlow today</p>
        </div>
        <c:if test="${error != null}"><div class="error-alert">&#x26A0; ${error}</div></c:if>
        <form action="/register" method="post">
            <div class="form-row">
                <div class="input-group"><span class="input-icon">&#x1F464;</span><input type="text" name="name" placeholder="Full Name" required></div>
                <div class="input-group"><span class="input-icon">&#x1F4DE;</span><input type="text" name="phone" placeholder="Phone" required></div>
            </div>
            <div class="input-group"><span class="input-icon">&#x2709;</span><input type="email" name="email" placeholder="Email address" required></div>
            <div class="input-group"><span class="input-icon">&#x1F512;</span><input type="password" name="password" placeholder="Password" minlength="6" required></div>
            <div class="form-row">
                <div class="input-group"><input type="text" name="address" placeholder="Address"></div>
                <div class="input-group"><input type="text" name="city" placeholder="City"></div>
            </div>
            <button type="submit" class="btn-primary auth-btn">Create Account &#x2192;</button>
        </form>
        <div class="auth-footer">Already have an account? <a href="/login">Login</a></div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
