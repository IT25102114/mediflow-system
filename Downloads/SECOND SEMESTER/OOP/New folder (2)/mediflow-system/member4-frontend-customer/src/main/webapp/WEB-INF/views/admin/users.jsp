<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp"><jsp:param name="title" value="Manage Users"/></jsp:include>
<jsp:include page="../common/navbar.jsp"/>

<div class="admin-page">
    <div class="admin-sidebar">
        <div class="admin-logo">&#x1F48A; MediFlow Admin</div>
        <a href="/admin/dashboard" class="sidebar-btn">&#x1F4CA; Dashboard</a>
        <a href="/products" class="sidebar-btn">&#x1F48A; Products</a>
        <a href="/orders" class="sidebar-btn">&#x1F4E6; Orders</a>
        <a href="/admin/users" class="sidebar-btn active">&#x1F465; Users</a>
        <a href="/admin/categories" class="sidebar-btn">&#x1F3F7; Categories</a>
        <a href="/suppliers" class="sidebar-btn">&#x1F69A; Suppliers</a>
        <a href="/reviews" class="sidebar-btn">&#x2B50; Reviews</a>
    </div>
    <div class="admin-content">
        <h2>&#x1F465; User Management</h2>
        <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>

        <div class="admin-table">
            <table>
                <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Role</th><th>Status</th><th>Actions</th></tr></thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td>${u.id}</td>
                            <td><strong>${u.name}</strong></td>
                            <td>${u.email}</td>
                            <td>${u.phone}</td>
                            <td><span class="role-pill ${u.role}">${u.role}</span></td>
                            <td><span class="status-dot ${u.active ? 'active' : 'inactive'}">${u.active ? 'Active' : 'Inactive'}</span></td>
                            <td>
                                <div class="action-cell">
                                    <form action="/admin/users/delete/${u.id}" method="post" onsubmit="return confirm('Delete user ${u.name}?')">
                                        <button class="icon-btn danger" title="Delete">&#x1F5D1;</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${empty users}"><p class="muted center">No users found</p></c:if>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
