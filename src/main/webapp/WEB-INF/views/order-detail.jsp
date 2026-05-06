<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Order #${order.orderNumber}"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="orders-page" style="max-width:800px">
    <div class="breadcrumb"><a href="/">Home</a><span class="breadcrumb-sep">/</span><a href="/orders">Orders</a><span class="breadcrumb-sep">/</span><span>${order.orderNumber}</span></div>

    <div class="order-card" style="margin-bottom:24px">
        <div style="padding:24px">
            <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:20px">
                <div><h2 style="font-size:1.3rem;margin:0">${order.orderNumber}</h2><p style="color:var(--text-muted);font-size:0.85rem">${order.createdAt}</p></div>
                <span class="status-pill ${order.status}" style="font-size:0.9rem;padding:6px 16px">${order.status}</span>
            </div>

            <div class="detail-grid" style="margin-bottom:24px">
                <div>&#x1F4B3; Payment: <strong>${order.paymentMethod}</strong></div>
                <div>&#x1F4B0; Payment Status: <strong>${order.paymentStatus}</strong></div>
                <div>&#x1F4CD; Address: <strong>${order.shippingAddress}, ${order.shippingCity}</strong></div>
                <div>&#x1F4DE; Phone: <strong>${order.shippingPhone}</strong></div>
            </div>

            <div style="padding:16px;background:var(--bg-secondary);border-radius:var(--radius-sm);margin-bottom:16px">
                <h4 style="margin-bottom:12px">&#x1F4E6; Order Items</h4>
                <c:forEach var="item" items="${items}">
                    <div class="order-item-row"><span>&#x1F48A; ${item.productName} x ${item.quantity}</span><span style="font-weight:600">Rs. ${item.subtotal}</span></div>
                </c:forEach>
            </div>

            <div style="display:flex;justify-content:flex-end;padding-top:16px;border-top:1px solid var(--border)">
                <div class="summary-total" style="font-size:1.3rem"><span style="margin-right:16px">Total:</span><span style="color:var(--accent)">Rs. ${order.totalAmount}</span></div>
            </div>

            <c:if test="${sessionScope.userRole == 'admin'}">
                <div style="margin-top:20px;padding-top:16px;border-top:1px solid var(--border)">
                    <form action="/orders/${order.id}/status" method="post" style="display:flex;gap:12px;align-items:center">
                        <label style="font-size:0.85rem;color:var(--text-secondary)">Update Status:</label>
                        <select name="status" class="status-select">
                            <option value="pending" ${order.status=='pending'?'selected':''}>Pending</option>
                            <option value="confirmed" ${order.status=='confirmed'?'selected':''}>Confirmed</option>
                            <option value="processing" ${order.status=='processing'?'selected':''}>Processing</option>
                            <option value="shipped" ${order.status=='shipped'?'selected':''}>Shipped</option>
                            <option value="delivered" ${order.status=='delivered'?'selected':''}>Delivered</option>
                            <option value="cancelled" ${order.status=='cancelled'?'selected':''}>Cancelled</option>
                        </select>
                        <button type="submit" class="btn-primary" style="padding:8px 18px;font-size:0.85rem">Update</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
