<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="navbar">
    <div class="navbar-container">
        <a href="/" class="navbar-brand">
            <span class="brand-icon">&#x1F48A;</span>
            <span class="brand-text">Medi<span class="brand-accent">Flow</span></span>
        </a>

        <form class="navbar-search" action="/products" method="get">
            <span class="search-icon">&#x1F50D;</span>
            <input type="text" name="search" placeholder="Search medicines, vitamins..." value="${param.search}">
        </form>

        <div class="navbar-links">
            <a href="/">Home</a>
            <a href="/products">Products</a>
            <a href="/suppliers">Suppliers</a>
            <a href="/reviews">Reviews</a>
            <c:if test="${sessionScope.userId != null}">
                <a href="/orders">&#x1F4E6; Orders</a>
                <c:if test="${sessionScope.userRole == 'admin'}">
                    <a href="/admin/dashboard" class="admin-link">&#x2699; Admin</a>
                </c:if>
            </c:if>
        </div>

        <div class="navbar-actions">
            <c:choose>
                <c:when test="${sessionScope.userId != null}">
                    <div class="user-menu-wrapper" onclick="this.classList.toggle('open')">
                        <button class="user-btn" type="button">
                            &#x1F464; <span class="user-name">${sessionScope.userName}</span>
                        </button>
                        <div class="user-dropdown" id="userDropdown">
                            <div class="dropdown-header">
                                <strong>${sessionScope.userName}</strong>
                                <c:if test="${sessionScope.userRole == 'admin'}">
                                    <span class="role-badge">Admin</span>
                                </c:if>
                            </div>
                            <a href="/account">&#x1F464; My Account</a>
                            <a href="/orders">&#x1F4E6; My Orders</a>
                            <c:if test="${sessionScope.userRole == 'admin'}">
                                <a href="/admin/dashboard">&#x2699; Dashboard</a>
                            </c:if>
                            <a href="/logout" style="color:var(--danger)">&#x1F6AA; Logout</a>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="/login" class="login-btn">Login</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
