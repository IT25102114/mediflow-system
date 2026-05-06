<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Reviews"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="products-page">
    <div class="products-header">
        <h1>&#x2B50; Customer Reviews</h1>
        <p>See what our customers say about MediFlow</p>
    </div>
    <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>

    <c:if test="${sessionScope.userId != null}">
        <div style="margin-bottom:24px"><a href="/reviews/add" class="btn-primary">&#x270D; Write a Review</a></div>
    </c:if>

    <div class="products-grid">
        <c:forEach var="r" items="${reviews}">
            <div class="review-card">
                <div style="display:flex;justify-content:space-between;align-items:flex-start;margin-bottom:12px">
                    <div style="display:flex;align-items:center;gap:12px">
                        <div style="width:42px;height:42px;border-radius:50%;background:linear-gradient(135deg,var(--accent),#0ea5e9);display:flex;align-items:center;justify-content:center;font-weight:800;color:#080d18;font-size:1rem">
                            ${r.userName != null && r.userName.length() > 0 ? r.userName.substring(0,1) : 'U'}
                        </div>
                        <div>
                            <strong style="font-size:0.95rem">${r.userName}</strong>
                            <p style="font-size:0.78rem;color:var(--text-muted)">${r.createdAt}</p>
                        </div>
                    </div>
                    <span class="status-pill ${r.getClass().simpleName == 'ProductReview' ? 'confirmed' : 'processing'}">
                        ${r.getClass().simpleName == 'ProductReview' ? 'Product' : 'Service'}
                    </span>
                </div>

                <div style="display:flex;gap:4px;margin-bottom:10px">
                    <c:forEach begin="1" end="5" var="i">
                        <span style="color:${i <= r.rating ? '#fbbf24' : 'var(--text-muted)'};font-size:1rem">&#x2B50;</span>
                    </c:forEach>
                </div>

                <c:if test="${r.getClass().simpleName == 'ProductReview'}">
                    <p style="font-size:0.8rem;color:var(--accent);font-weight:600;margin-bottom:6px">&#x1F48A; ${r.productName}</p>
                </c:if>
                <c:if test="${r.getClass().simpleName == 'ServiceReview'}">
                    <p style="font-size:0.8rem;color:var(--purple);font-weight:600;margin-bottom:6px">&#x1F6E0; ${r.serviceType}</p>
                </c:if>

                <p style="font-size:0.88rem;color:var(--text-secondary);line-height:1.6">${r.comment}</p>

                <c:if test="${sessionScope.userRole == 'admin'}">
                    <form action="/reviews/delete/${r.id}" method="post" onsubmit="return confirm('Delete?')" style="margin-top:12px">
                        <button class="cancel-order-btn" style="margin-top:0;font-size:0.8rem">&#x1F5D1; Delete</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty reviews}">
        <div class="empty-state"><span class="empty-icon">&#x2B50;</span><h3>No reviews yet</h3><p>Be the first to share your experience!</p>
            <c:if test="${sessionScope.userId != null}"><a href="/reviews/add" class="btn-primary">Write a Review</a></c:if>
        </div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp"/>
