<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="${editing ? 'Edit' : 'Add'} Product"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="products-page" style="max-width:800px">
    <div class="products-header"><h1>${editing ? '&#x270F; Edit' : '+ Add'} Medicine</h1></div>

    <div class="form-section">
        <form action="${editing ? '/products/update/'.concat(product.id) : '/products/add'}" method="post">
            <div class="form-row">
                <div class="form-group"><label>Medicine Name *</label><input type="text" name="name" value="${product.name}" required></div>
                <div class="form-group"><label>Manufacturer *</label><input type="text" name="manufacturer" value="${product.manufacturer}" required></div>
            </div>
            <div class="form-group"><label>Description</label><textarea name="description" rows="3">${product.description}</textarea></div>
            <div class="form-row">
                <div class="form-group"><label>Price (Rs.) *</label><input type="number" step="0.01" name="price" value="${product.price}" required></div>
                <div class="form-group"><label>Compare Price (Rs.)</label><input type="number" step="0.01" name="comparePrice" value="${product.comparePrice}"></div>
            </div>
            <div class="form-row">
                <div class="form-group"><label>Stock Quantity *</label><input type="number" name="stockQuantity" value="${product.stockQuantity}" required></div>
                <div class="form-group"><label>Category</label>
                    <select name="categoryId">
                        <option value="0">Select Category</option>
                        <c:forEach var="cat" items="${categories}"><option value="${cat.id}" ${product.categoryId == cat.id ? 'selected' : ''}>${cat.name}</option></c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group"><label>Type *</label>
                    <select name="type">
                        <option value="OTC" ${product.getProductType() == 'OTC' ? 'selected' : ''}>OTC (Over-the-Counter)</option>
                        <option value="PRESCRIPTION" ${product.getProductType() == 'PRESCRIPTION' ? 'selected' : ''}>Prescription Medicine</option>
                    </select>
                </div>
                <div class="form-group"><label>SKU</label><input type="text" name="sku" value="${product.sku}"></div>
            </div>
            <div class="form-row">
                <div class="form-group"><label>Dosage</label><input type="text" name="dosage" value="${product.dosage}" placeholder="e.g. 500mg"></div>
                <div class="form-group"><label>Weight</label><input type="text" name="weight" value="${product.weight}" placeholder="e.g. 100g"></div>
            </div>
            <div class="form-row">
                <div class="form-group"><label>Batch Number</label><input type="text" name="batchNumber" value="${product.batchNumber}"></div>
                <div class="form-group"><label>Expiry Date</label><input type="text" name="expiryDate" value="${product.expiryDate}" placeholder="YYYY-MM-DD"></div>
            </div>
            <div class="form-group"><label>Image URL</label><input type="text" name="imageUrl" value="${product.imageUrl}" placeholder="https://..."></div>
            <div class="form-group"><label>Side Effects</label><textarea name="sideEffects" rows="2">${product.sideEffects}</textarea></div>
            <div class="form-group"><label>Usage Instructions</label><textarea name="usageInstructions" rows="2">${product.usageInstructions}</textarea></div>

            <div style="display:flex;gap:8px;margin-top:8px">
                <label style="display:flex;align-items:center;gap:6px;cursor:pointer;font-size:0.9rem;color:var(--text-secondary)">
                    <input type="checkbox" name="featured" ${product.featured ? 'checked' : ''} style="accent-color:var(--accent);width:16px;height:16px"> Featured Product
                </label>
                <label style="display:flex;align-items:center;gap:6px;cursor:pointer;font-size:0.9rem;color:var(--text-secondary)">
                    <input type="checkbox" name="active" ${product.active ? 'checked' : ''} checked style="accent-color:var(--accent);width:16px;height:16px"> Active
                </label>
            </div>

            <div style="display:flex;gap:12px;margin-top:24px">
                <button type="submit" class="btn-primary">${editing ? 'Update' : 'Add'} Medicine</button>
                <a href="/products" class="btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>

<jsp:include page="common/footer.jsp"/>
