<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="${editing ? 'Edit' : 'Add'} Supplier"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="products-page" style="max-width:700px">
    <div class="products-header"><h1>${editing ? '&#x270F; Edit' : '+ Add'} Supplier</h1></div>

    <div class="form-section">
        <form action="/suppliers/add" method="post">
            <div class="form-row">
                <div class="form-group"><label>Supplier Name</label><input type="text" name="name" value="${supplier.name}" required></div>
                <div class="form-group"><label>Phone</label><input type="text" name="phone" value="${supplier.contactPhone}" required></div>
            </div>
            <div class="form-row">
                <div class="form-group"><label>Email</label><input type="email" name="email" value="${supplier.email}" required></div>
                <div class="form-group"><label>Address</label><input type="text" name="address" value="${supplier.address}" required></div>
            </div>
            <div class="form-group">
                <label>Supplier Type</label>
                <select name="type" id="supplierType" onchange="toggleType()">
                    <option value="local" ${isLocal != null && isLocal ? 'selected' : ''}>Local Supplier</option>
                    <option value="international" ${isLocal != null && !isLocal ? 'selected' : ''}>International Supplier</option>
                </select>
            </div>
            <div id="localFields" style="display:block">
                <div class="form-row">
                    <div class="form-group"><label>District</label><input type="text" name="extra1" placeholder="e.g. Colombo"></div>
                    <div class="form-group"><label>License Number</label><input type="text" name="extra2" placeholder="e.g. PHL-2024-001"></div>
                </div>
            </div>
            <div id="internationalFields" style="display:none">
                <div class="form-row">
                    <div class="form-group"><label>Country</label><input type="text" name="extra1" placeholder="e.g. India"></div>
                    <div class="form-group"><label>Import License</label><input type="text" name="extra2" placeholder="e.g. IMP-2024-001"></div>
                </div>
                <div class="form-group"><label>Currency</label><input type="text" name="extra3" placeholder="e.g. USD"></div>
            </div>
            <div style="display:flex;gap:12px;margin-top:20px">
                <button type="submit" class="btn-primary">${editing ? 'Update' : 'Add'} Supplier</button>
                <a href="/suppliers" class="btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>
<script>
function toggleType(){var t=document.getElementById('supplierType').value;document.getElementById('localFields').style.display=t==='local'?'block':'none';document.getElementById('internationalFields').style.display=t==='international'?'block':'none';}
toggleType();
</script>

<jsp:include page="common/footer.jsp"/>
