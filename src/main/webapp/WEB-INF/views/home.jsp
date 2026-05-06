<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Home"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<section class="hero">
    <div class="hero-bg-effect"></div>
    <div class="hero-content">
        <div class="hero-text">
            <div class="hero-badge">&#x1F3E5; Trusted Online Pharmacy</div>
            <h1>Your Health, <span class="gradient-text">Delivered</span></h1>
            <p>Sri Lanka's premium online medical store. Get genuine medicines, vitamins & healthcare products delivered to your doorstep with guaranteed quality and safety.</p>
            <div class="hero-actions">
                <a href="/products" class="btn-primary">Browse Medicines &#x2192;</a>
                <a href="/register" class="btn-secondary">Create Account</a>
            </div>
            <div class="hero-stats">
                <div class="stat"><strong>5000+</strong><span>Products</span></div>
                <div class="stat"><strong>50K+</strong><span>Happy Customers</span></div>
                <div class="stat"><strong>24/7</strong><span>Support</span></div>
            </div>
        </div>
        <div class="hero-visual-wrap">
            <img src="/images/hero-visual.png" alt="MediFlow" class="hero-main-img">
            <div class="hero-float-card card-tl">
                <span class="float-icon">&#x1F6E1;</span>
                <div><strong>100% Genuine</strong><span>Certified medicines</span></div>
            </div>
            <div class="hero-float-card card-br">
                <span class="float-icon">&#x1F69A;</span>
                <div><strong>Free Delivery</strong><span>Above Rs. 5000</span></div>
            </div>
            <div class="hero-float-pill pill-top">&#x2705; Pharmacist Verified</div>
            <div class="hero-float-rating">
                <span class="star-filled">&#x2B50;</span>
                <span class="star-filled">&#x2B50;</span>
                <span class="star-filled">&#x2B50;</span>
                <span class="star-filled">&#x2B50;</span>
                <span class="star-filled">&#x2B50;</span>
                <span>4.9 (12k+ reviews)</span>
            </div>
        </div>
    </div>
</section>

<section class="section categories-section">
    <div class="section-header">
        <h2>Shop by Category</h2>
        <a href="/products" class="see-all">See All &#x2192;</a>
    </div>
    <div class="categories-grid">
        <c:forEach var="cat" items="${categories}">
            <a href="/products?category=${cat.id}" class="category-card">
                <span class="cat-icon">${cat.icon != null ? cat.icon : '&#x1F48A;'}</span>
                <h4>${cat.name}</h4>
                <p>${cat.description}</p>
            </a>
        </c:forEach>
    </div>
</section>

<section class="section featured-section">
    <div class="section-header">
        <h2>Featured Products</h2>
        <a href="/products" class="see-all">View All &#x2192;</a>
    </div>
    <div class="products-grid">
        <c:forEach var="product" items="${featuredProducts}">
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
                        <c:otherwise>
                            <div class="placeholder-img">&#x1F48A;</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="product-info">
                    <p class="product-category">${product.categoryName != null ? product.categoryName : 'Medicine'}</p>
                    <h3 class="product-name">${product.name}</h3>
                    <p class="product-manufacturer">${product.manufacturer}</p>
                    <div class="product-pricing">
                        <span class="product-price">Rs. ${product.price}</span>
                        <c:if test="${product.comparePrice > product.price}">
                            <span class="compare-price">Rs. ${product.comparePrice}</span>
                        </c:if>
                    </div>
                    <c:choose>
                        <c:when test="${product.stockQuantity > 0}">
                            <a href="/orders/checkout" class="add-cart-btn">&#x1F6D2; Add to Cart</a>
                        </c:when>
                        <c:otherwise>
                            <button class="out-of-stock-btn" disabled>Out of Stock</button>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${product.stockQuantity > 0 && product.stockQuantity <= 10}">
                        <p class="low-stock-text">Only ${product.stockQuantity} left!</p>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty featuredProducts}">
            <div class="empty-state"><span class="empty-icon">&#x1F48A;</span><h3>No featured products yet</h3><p>Add some from the admin panel!</p></div>
        </c:if>
    </div>
</section>

<section class="features-banner">
    <div class="feature-item"><span>&#x1F69A;</span><div><h4>Free Delivery</h4><p>Orders above Rs. 5,000</p></div></div>
    <div class="feature-item"><span>&#x1F512;</span><div><h4>Secure Payments</h4><p>100% protected checkout</p></div></div>
    <div class="feature-item"><span>&#x1F4E6;</span><div><h4>Easy Returns</h4><p>7-day return policy</p></div></div>
    <div class="feature-item"><span>&#x1F4AC;</span><div><h4>24/7 Support</h4><p>Chat with pharmacists</p></div></div>
</section>

<jsp:include page="common/footer.jsp"/>
