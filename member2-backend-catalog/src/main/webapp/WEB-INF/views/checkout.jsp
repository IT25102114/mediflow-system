<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Checkout"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="checkout-page">
    <h1>&#x1F6D2; Checkout</h1>
    <c:if test="${error != null}"><div class="error-alert">${error}</div></c:if>

    <div class="checkout-layout">
        <div class="checkout-form">
            <form action="/orders/place" method="post">
                <div class="form-section">
                    <h3>&#x1F4E6; Shipping Information</h3>
                    <div class="form-row">
                        <div class="form-group"><label>Full Name</label><input type="text" name="shippingName" value="${sessionScope.userName}" required></div>
                        <div class="form-group"><label>Phone</label><input type="text" name="shippingPhone" required></div>
                    </div>
                    <div class="form-group"><label>Address</label><textarea name="shippingAddress" rows="2" required></textarea></div>
                    <div class="form-group"><label>City</label><input type="text" name="shippingCity" required></div>
                </div>
                <div class="form-section">
                    <h3>&#x1F4B3; Payment Method</h3>
                    <label class="payment-option selected"><input type="radio" name="paymentMethod" value="cod" checked> &#x1F4B5; Cash on Delivery</label>
                    <label class="payment-option"><input type="radio" name="paymentMethod" value="card"> &#x1F4B3; Card Payment</label>
                    <label class="payment-option"><input type="radio" name="paymentMethod" value="bank"> &#x1F3E6; Bank Transfer</label>
                </div>
                <div class="form-section">
                    <h3>&#x1F48A; Select Product</h3>
                    <div class="form-group"><label>Product</label>
                        <select name="productId" required>
                            <c:forEach var="p" items="${products}"><option value="${p.id}">&#x1F48A; ${p.name} - Rs. ${p.formattedPrice}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group"><label>Quantity</label><input type="number" name="quantity" value="1" min="1" required></div>
                </div>
                <button type="submit" class="btn-primary place-order-btn">&#x1F4E6; Place Order</button>
            </form>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
