<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Suppliers"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="products-page">
    <div class="products-header">
        <h1>&#x1F69A; Our Suppliers</h1>
        <p>Trusted pharmaceutical suppliers and partners</p>
    </div>
    <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>

    <div class="products-toolbar">
        <div class="search-box">
            <span style="position:absolute;left:12px;top:50%;transform:translateY(-50%);color:var(--text-muted)">&#x1F50D;</span>
            <form action="/suppliers" method="get" style="width:100%">
                <input type="text" name="search" placeholder="Search suppliers..." value="${search}" style="padding-left:36px;width:100%">
            </form>
        </div>
        <c:if test="${sessionScope.userRole == 'admin'}">
            <a href="/suppliers/add" class="btn-primary">+ Add Supplier</a>
        </c:if>
    </div>

    <div class="products-grid">
        <c:forEach var="s" items="${suppliers}">
            <div class="supplier-card">
                <div style="display:flex;align-items:center;gap:12px;margin-bottom:16px">
                    <div style="width:48px;height:48px;border-radius:50%;background:var(--accent-light);display:flex;align-items:center;justify-content:center;font-size:1.4rem;color:var(--accent);flex-shrink:0">
                        <c:choose><c:when test="${s.getClass().simpleName == 'LocalSupplier'}">&#x1F3E0;</c:when><c:otherwise>&#x1F30D;</c:otherwise></c:choose>
                    </div>
                    <div>
                        <h3 style="font-size:1rem;font-weight:700;margin:0">${s.name}</h3>
                        <span class="status-pill ${s.getClass().simpleName == 'LocalSupplier' ? 'confirmed' : 'processing'}">
                            ${s.getClass().simpleName == 'LocalSupplier' ? 'Local' : 'International'}
                        </span>
                    </div>
                </div>
                <div style="display:flex;flex-direction:column;gap:6px;font-size:0.85rem;color:var(--text-secondary);margin-bottom:16px">
                    <p>&#x1F4DE; ${s.contactPhone}</p>
                    <p>&#x2709; ${s.email}</p>
                    <p>&#x1F4CD; ${s.address}</p>
                </div>

                <c:if test="${s.getClass().simpleName == 'LocalSupplier'}">
                    <div style="padding:10px;background:var(--bg-secondary);border-radius:var(--radius-sm);font-size:0.8rem;color:var(--text-muted);margin-bottom:12px">
                        <strong>District:</strong> ${s.district} &bull; <strong>Reg. No:</strong> ${s.registrationNumber}
                    </div>
                </c:if>
                <c:if test="${s.getClass().simpleName == 'InternationalSupplier'}">
                    <div style="padding:10px;background:var(--bg-secondary);border-radius:var(--radius-sm);font-size:0.8rem;color:var(--text-muted);margin-bottom:12px">
                        <strong>Country:</strong> ${s.country} &bull; <strong>Import License:</strong> ${s.importLicenseNumber} &bull; <strong>Currency:</strong> ${s.currency}
                    </div>
                </c:if>

                <c:if test="${sessionScope.userRole == 'admin'}">
                    <div style="display:flex;gap:8px;margin-top:8px">
                        <a href="/suppliers/edit/${s.id}" class="btn-secondary" style="padding:8px 16px;font-size:0.82rem">&#x270F; Edit</a>
                        <form action="/suppliers/delete/${s.id}" method="post" onsubmit="return confirm('Delete?')">
                            <button class="cancel-order-btn" style="margin-top:0">&#x1F5D1; Delete</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty suppliers}">
        <div class="empty-state"><span class="empty-icon">&#x1F69A;</span><h3>No suppliers found</h3><p>Add your first supplier to get started</p></div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp"/>
