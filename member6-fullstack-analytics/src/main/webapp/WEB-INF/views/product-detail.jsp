<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="${product.name}"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="product-detail-page">
    <div class="breadcrumb">
        <a href="/">Home</a><span class="breadcrumb-sep">/</span>
        <a href="/products">Products</a><span class="breadcrumb-sep">/</span>
        <span>${product.name}</span>
    </div>

    <div class="product-detail-layout">
        <div class="product-detail-image">
            <c:choose>
                <c:when test="${product.imageUrl != null && !product.imageUrl.isEmpty()}"><img src="${product.imageUrl}" alt="${product.name}"></c:when>
                <c:otherwise><div class="detail-placeholder-img">&#x1F48A;</div></c:otherwise>
            </c:choose>
        </div>

        <div class="product-detail-info">
            <span class="detail-category">${product.categoryName != null ? product.categoryName : product.getProductType()}</span>
            <h1>${product.name}</h1>
            <p class="detail-manufacturer">by ${product.manufacturer}</p>

            <c:if test="${product.rating > 0}">
                <div class="detail-rating">
                    <c:forEach begin="1" end="5" var="i">
                        <span class="${i <= product.rating ? 'star-filled' : 'star-empty'}">&#x2B50;</span>
                    </c:forEach>
                    <span>${product.rating}</span><small>(${product.reviewCount} reviews)</small>
                </div>
            </c:if>

            <div class="detail-pricing">
                <span class="detail-price">Rs. ${product.formattedPrice}</span>
                <c:if test="${product.comparePrice > product.price}">
                    <span class="detail-compare">Rs. ${product.formattedComparePrice}</span>
                    <span class="detail-discount-text">Save ${product.discountPercentage}%</span>
                </c:if>
            </div>

            <c:if test="${product.dosage != null && !product.dosage.isEmpty()}">
                <div class="detail-dosage">&#x1F48A; Dosage: ${product.dosage}</div>
            </c:if>

            <div class="detail-stock">
                <c:choose>
                    <c:when test="${product.stockQuantity > 0}"><span class="in-stock-badge">&#x2705; In Stock (${product.stockQuantity})</span></c:when>
                    <c:otherwise><span class="out-of-stock-badge">&#x274C; Out of Stock</span></c:otherwise>
                </c:choose>
                <c:if test="${product.stockQuantity > 0 && product.stockQuantity <= 10}"><span class="low-stock-warning">Only ${product.stockQuantity} left!</span></c:if>
            </div>

            <c:if test="${product.requiresPrescription()}">
                <div class="prescription-notice">&#x26A0; This medicine requires a valid prescription</div>
            </c:if>

            <div class="detail-meta-grid">
                <c:if test="${product.sku != null && !product.sku.isEmpty()}"><div class="meta-item"><span>SKU</span><strong>${product.sku}</strong></div></c:if>
                <c:if test="${product.weight != null && !product.weight.isEmpty()}"><div class="meta-item"><span>Weight</span><strong>${product.weight}</strong></div></c:if>
                <c:if test="${product.batchNumber != null && !product.batchNumber.isEmpty()}"><div class="meta-item"><span>Batch</span><strong>${product.batchNumber}</strong></div></c:if>
                <c:if test="${product.expiryDate != null && !product.expiryDate.isEmpty()}"><div class="meta-item"><span>Expiry</span><strong>${product.expiryDate}</strong></div></c:if>
                <div class="meta-item"><span>Type</span><strong>${product.getProductType()}</strong></div>
            </div>

            <c:if test="${sessionScope.userRole == 'admin'}">
                <div style="display:flex;gap:12px;margin-top:16px">
                    <a href="/products/edit/${product.id}" class="btn-primary" style="padding:10px 20px">&#x270F; Edit</a>
                    <form action="/products/delete/${product.id}" method="post" onsubmit="return confirm('Delete?')">
                        <button class="btn-secondary" style="padding:10px 20px;color:var(--danger);border-color:var(--danger)">&#x1F5D1; Delete</button>
                    </form>
                </div>
            </c:if>
        </div>
    </div>

    <c:if test="${product.description != null && !product.description.isEmpty()}">
        <div class="detail-tabs-section">
            <div class="detail-tabs"><button class="detail-tab-btn active">Description</button></div>
            <div class="tab-pane"><p>${product.description}</p></div>
        </div>
    </c:if>

    <c:if test="${product.sideEffects != null && !product.sideEffects.isEmpty()}">
        <div class="detail-tabs-section">
            <div class="detail-tabs"><button class="detail-tab-btn active">Side Effects</button></div>
            <div class="tab-pane warning-block"><h4>&#x26A0; Side Effects</h4><p>${product.sideEffects}</p></div>
        </div>
    </c:if>
</div>

<jsp:include page="common/footer.jsp"/>
