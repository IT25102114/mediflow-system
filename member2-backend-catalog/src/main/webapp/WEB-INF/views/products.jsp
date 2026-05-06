<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Products"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="products-page">
    <div class="products-header">
        <h1>All Products</h1>
        <p>${products.size()} products found</p>
    </div>
    <c:if test="${success != null}"><div class="alert alert-success">${success}</div></c:if>

    <div class="products-toolbar">
        <div class="search-box">
            <span style="position:absolute;left:12px;top:50%;transform:translateY(-50%);color:var(--text-muted)">&#x1F50D;</span>
            <form action="/products" method="get" style="width:100%">
                <input type="text" name="search" placeholder="Search medicines..." value="${search}" style="padding-left:36px;width:100%">
            </form>
        </div>
        <form action="/products" method="get">
            <select name="category" onchange="this.form.submit()">
                <option value="">All Categories</option>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}" ${selectedCategory == cat.id ? 'selected' : ''}>${cat.name}</option>
                </c:forEach>
            </select>
        </form>
    </div>

    <c:if test="${sessionScope.userRole == 'admin'}">
        <div style="margin-bottom:24px"><a href="/products/add" class="btn-primary">+ Add Medicine</a></div>
    </c:if>

    <div class="products-grid">
        <c:forEach var="product" items="${products}">
            <div class="product-card" onclick="window.location='/products/${product.id}'">
                <c:if test="${product.comparePrice > product.price}">
                    <span class="discount-badge">-${product.discountPercentage}%</span>
                </c:if>
                <c:if test="${product.requiresPrescription()}">
                    <span class="prescription-badge">&#x26A0; Rx</span>
                </c:if>
                <div class="product-image">
                    <c:choose>
                        <c:when test="${product.imageUrl != null && !product.imageUrl.isEmpty()}">
                            <img src="${product.imageUrl}" alt="${product.name}">
                        </c:when>
                        <c:otherwise><div class="placeholder-img">&#x1F48A;</div></c:otherwise>
                    </c:choose>
                </div>
                <div class="product-info">
                    <p class="product-category">Medicine</p>
                    <h3 class="product-name">${product.name}</h3>
                    <p class="product-manufacturer">${product.manufacturer}</p>
                    <div class="product-pricing">
                        <span class="product-price">Rs. ${product.formattedPrice}</span>
                        <c:if test="${product.comparePrice > product.price}">
                            <span class="compare-price">Rs. ${product.formattedComparePrice}</span>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${product.stockQuantity > 0}">
                            <button class="add-cart-btn" onclick="event.stopPropagation();window.location='/products/${product.id}'">&#x1F6D2; View Details</button>
                        </c:when>
                        <c:otherwise><button class="out-of-stock-btn" disabled>Out of Stock</button></c:otherwise>
                    </c:choose>
                    <c:if test="${product.stockQuantity > 0 && product.stockQuantity <= 10}">
                        <p class="low-stock-text">Only ${product.stockQuantity} left!</p>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
    <c:if test="${empty products}">
        <div class="empty-state"><span class="empty-icon">&#x1F50D;</span><h3>No products found</h3><p>Try adjusting your search or filter</p></div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp"/>
