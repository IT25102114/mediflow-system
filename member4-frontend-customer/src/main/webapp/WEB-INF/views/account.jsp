<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="My Account"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="account-page">
    <div class="account-layout">
        <div class="account-sidebar">
            <div class="account-avatar">
                <div class="avatar-circle">${sessionScope.userName != null ? sessionScope.userName.substring(0,1) : 'U'}</div>
                <h3>${sessionScope.userName}</h3>
                <p style="color:var(--text-muted);font-size:0.8rem">${sessionScope.userRole}</p>
            </div>
            <div class="account-nav">
                <a href="/account" class="account-nav-btn active">&#x1F464; Profile</a>
                <a href="/orders" class="account-nav-btn">&#x1F4E6; Orders</a>
                <c:if test="${sessionScope.userRole == 'admin'}"><a href="/admin/dashboard" class="account-nav-btn">&#x2699; Admin</a></c:if>
                <a href="/logout" class="logout-btn-full">&#x1F6AA; Logout</a>
            </div>
        </div>
        <div class="account-content">
            <div class="tab-title"><h2>&#x1F464; Profile Details</h2></div>
            <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>
            <div class="profile-grid">
                <div class="profile-field"><label>&#x1F464; Full Name</label><p>${user.name}</p></div>
                <div class="profile-field"><label>&#x2709; Email</label><p>${user.email}</p></div>
                <div class="profile-field"><label>&#x1F4DE; Phone</label><p>${user.phone}</p></div>
                <div class="profile-field"><label>&#x1F3F7; Role</label><p><span class="role-pill ${user.role}">${user.role}</span></p></div>
                <div class="profile-field"><label>&#x1F4CD; Address</label><p>${user.address != null ? user.address : 'Not set'}</p></div>
                <div class="profile-field"><label>&#x1F3D9; City</label><p>${user.city != null ? user.city : 'Not set'}</p></div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
