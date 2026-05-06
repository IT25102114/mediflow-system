<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Orders"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="orders-page">
    <h1>&#x1F4E6; My Orders</h1>
    <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>
    <div class="orders-list">
        <c:forEach var="order" items="${orders}">
            <div class="order-card">
                <div class="order-header" onclick="this.nextElementSibling.style.display=this.nextElementSibling.style.display==='none'?'block':'none'">
                    <div class="order-info">
                        <h3>${order.orderNumber}</h3>
                        <small>${order.createdAt}</small>
                    </div>
                    <span class="status-pill ${order.status}">${order.status}</span>
                    <span class="order-total">Rs. ${order.totalAmount}</span>
                </div>
                <div class="order-details" style="display:none">
                    <div class="detail-grid">
                        <div>Payment: <strong>${order.paymentMethod}</strong></div>
                        <div>Status: <strong>${order.paymentStatus}</strong></div>
                        <div>Ship to: <strong>${order.shippingAddress}, ${order.shippingCity}</strong></div>
                        <div>Phone: <strong>${order.shippingPhone}</strong></div>
                    </div>
                    <a href="/orders/${order.id}" class="btn-primary" style="padding:10px 20px;font-size:0.85rem">View Details &#x2192;</a>
                    <c:if test="${order.status == 'pending'}">
                        <form action="/orders/${order.id}/cancel" method="post" style="display:inline" onsubmit="return confirm('Cancel?')">
                            <button class="cancel-order-btn">&#x2716; Cancel Order</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty orders}">
        <div class="empty-state"><span class="empty-icon">&#x1F4E6;</span><h3>No orders yet</h3><p>Start shopping to place your first order!</p><a href="/products" class="btn-primary">Browse Products</a></div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp"/>
