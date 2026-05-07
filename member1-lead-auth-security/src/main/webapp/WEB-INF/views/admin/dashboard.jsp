<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp"><jsp:param name="title" value="Admin Dashboard"/></jsp:include>
<jsp:include page="../common/navbar.jsp"/>

<div class="admin-page">
    <div class="admin-sidebar">
        <div class="admin-logo">&#x1F48A; MediFlow Admin</div>
        <a href="/admin/dashboard" class="sidebar-btn active">&#x1F4CA; Dashboard</a>
        <a href="/products" class="sidebar-btn">&#x1F48A; Products</a>
        <a href="/orders" class="sidebar-btn">&#x1F4E6; Orders</a>
        <a href="/admin/users" class="sidebar-btn">&#x1F465; Users</a>
        <a href="/admin/categories" class="sidebar-btn">&#x1F3F7; Categories</a>
        <a href="/suppliers" class="sidebar-btn">&#x1F69A; Suppliers</a>
        <a href="/reviews" class="sidebar-btn">&#x2B50; Reviews</a>
    </div>
    <div class="admin-content">
        <h2>Dashboard Overview</h2>
        <div class="stats-grid">
            <div class="stat-card revenue"><div class="stat-icon">&#x1F4B0;</div><div><p class="stat-label">Total Revenue</p><h3>Rs. ${revenue}</h3></div></div>
            <div class="stat-card orders"><div class="stat-icon">&#x1F4E6;</div><div><p class="stat-label">Total Orders</p><h3>${orderCount}</h3></div></div>
            <div class="stat-card users"><div class="stat-icon">&#x1F465;</div><div><p class="stat-label">Total Users</p><h3>${userCount}</h3></div></div>
            <div class="stat-card products"><div class="stat-icon">&#x1F48A;</div><div><p class="stat-label">Products</p><h3>${productCount}</h3></div></div>
        </div>

        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>&#x1F4E6; Recent Orders</h3>
                <div class="recent-orders-table">
                    <table><thead><tr><th>Order</th><th>Total</th><th>Status</th></tr></thead>
                    <tbody>
                        <c:forEach var="order" items="${recentOrders}">
                            <tr><td class="order-num"><a href="/orders/${order.id}">${order.orderNumber}</a></td>
                                <td>Rs. ${order.totalAmount}</td>
                                <td><span class="status-pill ${order.status}">${order.status}</span></td></tr>
                        </c:forEach>
                    </tbody></table>
                </div>
            </div>
            <div class="dashboard-card">
                <h3>&#x26A0; Low Stock Alerts</h3>
                <div class="alert-list">
                    <c:forEach var="p" items="${lowStock}">
                        <div class="alert-item">
                            <span>${p.name}</span>
                            <span class="stock-count danger">${p.stockQuantity} left</span>
                        </div>
                    </c:forEach>
                    <c:if test="${empty lowStock}"><p class="muted center">No low stock items</p></c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
