// MediFlow Chatbot - Smart FAQ Assistant
(function() {
    const BOT_NAME = 'MediBot';
    const RESPONSES = [
        { keys: ['hello','hi','hey','ayubowan','kohomada'], reply: "Ayubowan! 👋 Welcome to MediFlow. I'm MediBot, your pharmacy assistant. How can I help you today?\n\n• Ask about medicines\n• Order tracking\n• Delivery info\n• Prescription help" },
        { keys: ['order','track','tracking','delivery status'], reply: "📦 To track your order:\n1. Go to **Orders** page\n2. Click on your order number\n3. View real-time status\n\nStatuses: Pending → Confirmed → Processing → Shipped → Delivered" },
        { keys: ['delivery','shipping','deliver','ship'], reply: "🚚 **Delivery Info:**\n• Free delivery for orders above Rs. 5,000\n• Standard delivery: 2-3 business days\n• Express delivery: Same day (Colombo only)\n• We deliver island-wide across Sri Lanka!" },
        { keys: ['prescription','rx','doctor'], reply: "⚠️ **Prescription Medicines:**\nProducts marked with 'Rx' require a valid prescription. You can:\n1. Upload prescription during checkout\n2. Show it to delivery personnel\n3. Contact our pharmacist for guidance\n\nCall: +94 77 123 4567" },
        { keys: ['return','refund','exchange'], reply: "📦 **Return Policy:**\n• 7-day return policy on unopened items\n• Damaged items can be returned immediately\n• Refunds processed within 3-5 business days\n• Contact support for return requests" },
        { keys: ['payment','pay','card','cash'], reply: "💳 **Payment Methods:**\n• Cash on Delivery (COD)\n• Credit/Debit Card\n• Bank Transfer\n\nAll online payments are 100% secured with SSL encryption 🔒" },
        { keys: ['paracetamol','headache','fever','pain'], reply: "💊 **For Pain & Fever:**\nParacetamol 500mg is commonly used. Available on our Products page.\n⚠️ Dosage: 1-2 tablets every 4-6 hours\n⚠️ Max: 8 tablets per day\n\nAlways consult a doctor for persistent symptoms." },
        { keys: ['vitamin','supplement','immunity'], reply: "🍎 **Vitamins & Supplements:**\nWe carry Vitamin C, D, B-complex, Zinc, and more. Check our Vitamins & Supplements category!\n\nPopular: Vitamin C 1000mg for immunity boost." },
        { keys: ['antibiotic','infection','amoxicillin'], reply: "🦠 **Antibiotics:**\nAll antibiotics require a prescription (Rx). Common ones available:\n• Amoxicillin 250mg/500mg\n• Azithromycin\n• Cephalexin\n\n⚠️ Never self-prescribe antibiotics!" },
        { keys: ['register','account','sign up','signup'], reply: "👤 **Create Account:**\nVisit the Register page to create your free account!\nBenefits:\n• Track orders easily\n• Save delivery addresses\n• Get exclusive offers\n• Review products" },
        { keys: ['contact','support','help','phone'], reply: "📞 **Contact Us:**\n• Phone: +94 77 123 4567\n• Email: support@mediflow.lk\n• Address: Colombo 07, Sri Lanka\n• Available 24/7 for emergencies" },
        { keys: ['price','cost','cheap','expensive','discount'], reply: "💰 **Pricing:**\nWe offer competitive prices on all medicines. Look for discount badges on products!\n\n🏷️ Tips:\n• Check featured products for best deals\n• Free delivery on orders above Rs. 5,000\n• Compare prices shown on product cards" },
        { keys: ['supplier','manufacture','brand'], reply: "🏭 **Our Suppliers:**\nWe partner with trusted local & international suppliers:\n• PharmaCo Ltd (Local)\n• Lanka Pharma (Local)\n• GlaxoSmithKline (International)\n\nVisit our Suppliers page for more info!" },
        { keys: ['thank','thanks','thx'], reply: "You're welcome! 😊 Is there anything else I can help you with? Feel free to ask anytime!" },
        { keys: ['bye','goodbye','exit'], reply: "Goodbye! 👋 Thank you for choosing MediFlow. Stay healthy! 💚" },
    ];

    const DEFAULT_REPLY = "I'm not sure about that. Here's what I can help with:\n\n💊 Medicine information\n📦 Order tracking\n🚚 Delivery details\n💳 Payment methods\n📋 Prescriptions\n📞 Contact support\n\nTry asking about any of these topics!";

    function findResponse(msg) {
        const lower = msg.toLowerCase();
        for (const r of RESPONSES) {
            if (r.keys.some(k => lower.includes(k))) return r.reply;
        }
        return DEFAULT_REPLY;
    }

    function createChatbot() {
        // FAB button
        const fab = document.createElement('button');
        fab.className = 'chatbot-fab';
        fab.id = 'chatbotFab';
        fab.innerHTML = '💬<span class="fab-pulse"></span>';
        fab.onclick = () => { toggleChat(true); };
        document.body.appendChild(fab);

        // Chat window
        const win = document.createElement('div');
        win.className = 'chatbot-window';
        win.id = 'chatbotWindow';
        win.style.display = 'none';
        win.innerHTML = `
            <div class="chatbot-header">
                <div class="chatbot-header-info">
                    <span class="chatbot-avatar">🤖</span>
                    <div>
                        <h4>${BOT_NAME}</h4>
                        <div class="chatbot-status"><span class="status-dot-green"></span> Online</div>
                    </div>
                </div>
                <div class="chatbot-header-actions">
                    <button class="chatbot-icon-btn" onclick="document.getElementById('chatbotWindow').style.display='none';document.getElementById('chatbotFab').style.display='flex';">✕</button>
                </div>
            </div>
            <div class="chatbot-messages" id="chatMessages">
                <div class="chatbot-msg">
                    <span class="msg-avatar">🤖</span>
                    <div class="msg-bubble assistant">Ayubowan! 👋 I'm <strong>MediBot</strong>, your MediFlow pharmacy assistant. How can I help you today?</div>
                </div>
            </div>
            <form class="chatbot-input-form" id="chatForm" onsubmit="return false;">
                <input type="text" id="chatInput" placeholder="Ask about medicines, orders..." autocomplete="off">
                <button type="submit" class="chatbot-send-btn" id="chatSendBtn">➤</button>
            </form>
        `;
        document.body.appendChild(win);

        // Events
        document.getElementById('chatForm').addEventListener('submit', handleSend);
        document.getElementById('chatSendBtn').addEventListener('click', handleSend);
        document.getElementById('chatInput').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') handleSend(e);
        });
    }

    function toggleChat(open) {
        const win = document.getElementById('chatbotWindow');
        const fab = document.getElementById('chatbotFab');
        win.style.display = open ? 'flex' : 'none';
        fab.style.display = open ? 'none' : 'flex';
        if (open) document.getElementById('chatInput').focus();
    }

    function handleSend(e) {
        e.preventDefault();
        const input = document.getElementById('chatInput');
        const msg = input.value.trim();
        if (!msg) return;
        input.value = '';
        addMessage(msg, 'user');

        // Show typing
        const typing = addTyping();
        setTimeout(() => {
            typing.remove();
            const reply = findResponse(msg);
            addMessage(reply, 'assistant');
        }, 600 + Math.random() * 400);
    }

    function addMessage(text, role) {
        const container = document.getElementById('chatMessages');
        const div = document.createElement('div');
        div.className = 'chatbot-msg ' + role;
        const formatted = text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>').replace(/\n/g, '<br>');
        if (role === 'user') {
            div.innerHTML = `<div class="msg-bubble user">${formatted}</div>`;
        } else {
            div.innerHTML = `<span class="msg-avatar">🤖</span><div class="msg-bubble assistant">${formatted}</div>`;
        }
        container.appendChild(div);
        container.scrollTop = container.scrollHeight;
        return div;
    }

    function addTyping() {
        const container = document.getElementById('chatMessages');
        const div = document.createElement('div');
        div.className = 'chatbot-msg';
        div.innerHTML = `<span class="msg-avatar">🤖</span><div class="msg-bubble assistant"><div class="typing-indicator"><span></span><span></span><span></span></div></div>`;
        container.appendChild(div);
        container.scrollTop = container.scrollHeight;
        return div;
    }

    // Init on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', createChatbot);
    } else {
        createChatbot();
    }
})();
