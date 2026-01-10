<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width">
  <title>Shipping update</title>
  <style>
    /* Reset / base */
    body,table,td,a{ -webkit-text-size-adjust:100%; -ms-text-size-adjust:100%; }
    table,td{ mso-table-lspace:0pt; mso-table-rspace:0pt; }
    img{ -ms-interpolation-mode:bicubic; }
    img { border:0; height:auto; line-height:100%; outline:none; text-decoration:none; }
    body { margin:0; padding:0; width:100%; background-color:#f4f6f8; font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial; color:#333333; }
    a { color:#1f6feb; text-decoration:none; }
    /* Container */
    .email-wrapper { width:100%; background-color:#f4f6f8; padding:20px 0; }
    .email-content { max-width:680px; margin:0 auto; background:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 6px 18px rgba(16,24,40,0.06); }
    .email-header { padding:20px 24px; display:flex; align-items:center; gap:12px; border-bottom:1px solid #eef2f6; }
    .brand-logo { width:48px; height:48px; object-fit:contain; }
    .brand-name { font-size:18px; font-weight:700; color:#0f1724; }
    .preheader { display:none; max-height:0; max-width:0; opacity:0; overflow:hidden; mso-hide:all; font-size:1px; line-height:1px; color:#ffffff; }
    .email-body { padding:24px; }
    .status-card { border-radius:8px; padding:16px; background:#f8fafc; border:1px solid #eef2f6; display:flex; gap:12px; align-items:center; margin-bottom:16px; }
    .status-badge { display:inline-block; min-width:92px; padding:8px 12px; border-radius:999px; font-weight:700; text-align:center; }
    .status-RECEIVED { background:#eef2ff; color:#0f3bd6; }
    .status-IN_TRANSIT { background:#fff7ed; color:#b45309; }
    .status-OUT_FOR_DELIVERY { background:#ecfdf5; color:#047857; }
    .status-DELIVERED { background:#eef6ff; color:#0f3bd6; }
    .status-DELIVERY_FAILED { background:#fff1f2; color:#b91c1c; }
    .order-meta { font-size:13px; color:#525252; margin-bottom:12px; }
    .order-row { display:flex; justify-content:space-between; gap:12px; font-size:14px; }
    .items { border-top:1px dashed #eef2f6; padding-top:12px; margin-top:12px; }
    .item { display:flex; gap:12px; align-items:center; padding:8px 0; border-bottom:1px solid rgba(14,20,24,0.03); }
    .item:last-child { border-bottom:none; }
    .item-thumb { width:56px; height:56px; object-fit:cover; border-radius:6px; background:#f3f4f6; }
    .item-info { font-size:13px; color:#111827; }
    .cta { display:block; text-align:center; margin:18px 0 6px; }
    .btn { display:inline-block; padding:12px 20px; border-radius:8px; background:#1f6feb; color:#ffffff; font-weight:600; text-decoration:none; }
    .muted { color:#6b7280; font-size:13px; }
    .footer { padding:18px 24px; border-top:1px solid #eef2f6; background:#fafafa; font-size:13px; color:#6b7280; text-align:center; }
    .small { font-size:12px; color:#9ca3af; }
    /* Responsive */
    @media (max-width:520px) {
      .email-header, .email-body, .footer { padding-left:16px; padding-right:16px; }
      .order-row { flex-direction:column; align-items:flex-start; gap:6px; }
    }
  </style>
</head>
<body>
  <!-- Preheader: short summary shown in inbox preview -->
  <span class="preheader">Update: Your shipment {shippingId}</span>

  <div class="email-wrapper">
    <div class="email-content" role="article" aria-label="Shipping update">

      <!-- Body -->
      <div class="email-body">
        <!-- Status card -->
        <div class="status-card" role="status" aria-live="polite">
          <div>
            <div style="font-size:14px; color:#374151; margin-bottom:6px;">Hi ${name},</div>
            <div style="font-size:15px; font-weight:700; color:#0f1724;">Your order update</div>
            <div class="order-meta">We wanted to let you know the current status of your shipment.</div>
          </div>
        </div>

        <!-- Delivery address -->
        <div style="margin-top:14px;">
          <div class="small muted">Delivery address</div>
          <div style="margin-top:6px; font-size:14px;">
            ${city} ${state}<br>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="footer">
      </div>
    </div>
  </div>
</body>
</html>