# Fawry Quantum Internship Challenge 

## Overview

This project implements a *simple e-commerce system* in Java as per the Fawry Full Stack Development Internship Challenge requirements.

### Features
✅ Define products:
- Name, price, quantity.
- Some products can expire (e.g., Cheese, Biscuits).
- Some products require shipping and have weight (e.g., Cheese, TV).

✅ Cart functionality:
- Add products to the cart (quantity <= available stock).
- Check stock and expiration status.

✅ Checkout process:
- Print checkout receipt showing:
  - Subtotal
  - Shipping fees
  - Total paid amount
  - Customer balance after payment
- Print shipment notice if there are shippable items.
- Validate:
  - Cart is not empty
  - Sufficient customer balance
  - Items are not expired
  - Stock availability

✅ Shipping service:
- Accepts a list of items implementing the Shippable interface.

