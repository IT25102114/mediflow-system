<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="../common/header.jsp"><jsp:param name="title" value="Manage Categories"/></jsp:include>
<jsp:include page="../common/navbar.jsp"/>

<div class="admin-page">
    <div class="admin-sidebar">
        <div class="admin-logo">&#x1F48A; MediFlow Admin</div>
        <a href="/admin/dashboard" class="sidebar-btn">&#x1F4CA; Dashboard</a>
        <a href="/products" class="sidebar-btn">&#x1F48A; Products</a>
        <a href="/orders" class="sidebar-btn">&#x1F4E6; Orders</a>
        <a href="/admin/users" class="sidebar-btn">&#x1F465; Users</a>
        <a href="/admin/categories" class="sidebar-btn active">&#x1F3F7; Categories</a>
        <a href="/suppliers" class="sidebar-btn">&#x1F69A; Suppliers</a>
        <a href="/reviews" class="sidebar-btn">&#x2B50; Reviews</a>
    </div>
    <div class="admin-content">
        <div class="tab-header">
            <h2>&#x1F3F7; Category Management</h2>
        </div>
        <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>

        <div class="form-section" style="margin-bottom:32px;max-width:600px">
            <h3>Add New Category</h3>
            <form action="/admin/categories/add" method="post">
                <div class="form-row">
                    <div class="form-group"><label>Name</label><input type="text" name="name" required placeholder="e.g. Pain Relief"></div>
                    <div class="form-group"><label>Icon (emoji)</label><input type="text" name="icon" placeholder="e.g. &#x1F48A;"></div>
                </div>
                <div class="form-group"><label>Description</label><textarea name="description" rows="2" placeholder="Category description"></textarea></div>
                <button type="submit" class="btn-primary" style="padding:10px 24px">+ Add Category</button>
            </form>
        </div>

        <div class="admin-table">
            <table>
                <thead><tr><th>ID</th><th>Icon</th><th>Name</th><th>Description</th><th>Status</th><th>Actions</th></tr></thead>
                <tbody>
                    <c:forEach var="cat" items="${categories}">
                        <tr>
                            <td>${cat.id}</td>
                            <td style="font-size:1.4rem">${cat.icon}</td>
                            <td><strong>${cat.name}</strong></td>
                            <td style="max-width:300px;color:var(--text-secondary)">${cat.description}</td>
                            <td><span class="status-dot ${cat.active ? 'active' : 'inactive'}">${cat.active ? 'Active' : 'Inactive'}</span></td>
                            <td>
                                <form action="/admin/categories/delete/${cat.id}" method="post" onsubmit="return confirm('Delete?')">
                                    <button class="icon-btn danger" title="Delete">&#x1F5D1;</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${empty categories}"><p class="muted center">No categories found</p></c:if>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>
