<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Bookstore Notification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 30px auto;
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
        }
        h2 {
            color: #2c3e50;
        }
        p {
            font-size: 16px;
            line-height: 1.5;
        }
        .section {
            margin-bottom: 20px;
        }
        .highlight {
            font-weight: bold;
            color: #2980b9;
        }
        .status {
            font-weight: bold;
            color: #27ae60;
        }
        .status.denied {
            color: #c0392b;
        }
        hr {
            border: none;
            border-top: 1px solid #eee;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Purchase Notification -->
        <div class="section">
            <h2>Order Received</h2>
            <p>Hello <span class="highlight">${name}</span>,</p>
            <p>We have received your order for the book: <span class="highlight">${book}</span>.</p>
            <p>Thank you for shopping with us!</p>
        </div>
    </div>
</body>
</html>