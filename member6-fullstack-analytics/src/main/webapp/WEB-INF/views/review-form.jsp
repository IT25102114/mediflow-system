<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="common/header.jsp"><jsp:param name="title" value="Write Review"/></jsp:include>
<jsp:include page="common/navbar.jsp"/>

<div class="products-page" style="max-width:600px">
    <div class="products-header"><h1>&#x270D; Write a Review</h1></div>

    <div class="form-section">
        <form action="/reviews/add" method="post">
            <div class="form-group">
                <label>Review Type</label>
                <select name="type" id="reviewType" onchange="toggleReviewType()">
                    <option value="product">Product Review</option>
                    <option value="service">Service Review</option>
                </select>
            </div>
            <div id="productFields">
                <div class="form-row">
                    <div class="form-group"><label>Product ID</label><input type="number" name="productId" value="${productId}" required></div>
                    <div class="form-group"><label>Product Name</label><input type="text" name="productName" value="${productName}" required></div>
                </div>
            </div>
            <div class="form-group">
                <label>Rating</label>
                <div id="starRating" style="display:flex;gap:8px;font-size:1.8rem;cursor:pointer">
                    <span onclick="setRating(1)" data-star="1">&#x2B50;</span>
                    <span onclick="setRating(2)" data-star="2">&#x2B50;</span>
                    <span onclick="setRating(3)" data-star="3">&#x2B50;</span>
                    <span onclick="setRating(4)" data-star="4">&#x2B50;</span>
                    <span onclick="setRating(5)" data-star="5">&#x2B50;</span>
                </div>
                <input type="hidden" name="rating" id="ratingInput" value="5">
            </div>
            <div class="form-group"><label>Your Review</label><textarea name="comment" rows="4" placeholder="Share your experience..." required></textarea></div>
            <div style="display:flex;gap:12px;margin-top:20px">
                <button type="submit" class="btn-primary">Submit Review</button>
                <a href="/reviews" class="btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</div>
<script>
function setRating(n){document.getElementById('ratingInput').value=n;var stars=document.querySelectorAll('#starRating span');stars.forEach(function(s,i){s.style.opacity=i<n?'1':'0.3';});}
function toggleReviewType(){var t=document.getElementById('reviewType').value;document.getElementById('productFields').style.display=t==='product'?'block':'none';}
setRating(5);
</script>

<jsp:include page="common/footer.jsp"/>
