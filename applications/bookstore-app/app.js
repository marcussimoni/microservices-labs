// API Endpoints
const API_BASE_URL = 'http://localhost:8080/bookstore-service';
const API_ENDPOINTS = {
    BOOKS: `${API_BASE_URL}/books`,
    PURCHASES: `${API_BASE_URL}/purchases`,
    AUTHENTICATED_USER: 'http://localhost:8082/users/me'
};

// DOM Elements
const booksListEl = document.getElementById('booksList');
const bookDetailsEl = document.getElementById('bookDetails');
const bookInfoEl = document.getElementById('bookInfo');
const purchasedBooksEl = document.getElementById('purchasedBooks');
const purchasedListEl = document.getElementById('purchasedList');
const viewBooksBtn = document.getElementById('viewBooks');
const viewPurchasedBtn = document.getElementById('viewPurchased');
const backToListBtn = document.getElementById('backToList');
const notificationEl = document.getElementById('notification');

// State
let currentView = 'books'; // 'books', 'details', 'purchased'
let currentBookId = null;

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    loadBooks();
    loadAuthenticatedUser();
    viewBooksBtn.addEventListener('click', () => {
        showView('books');
        loadBooks();
    });

    viewPurchasedBtn.addEventListener('click', () => {
        showView('purchased');
        loadPurchasedBooks();
    });

    backToListBtn.addEventListener('click', () => {
        showView('books');
    });
});

async function loadAuthenticatedUser() {
    const response = await fetch(API_ENDPOINTS.AUTHENTICATED_USER);
    const user = await response.json()
    console.log("users", user)

    const customerName = document.getElementById('customerName');
    const customerEmail = document.getElementById('customerEmail');
    customerName.innerHTML = user.name
    customerEmail.innerHTML = user.email
}

// View Management
function showView(view) {
    // Hide all views first
    document.querySelectorAll('.content').forEach(el => el.classList.add('hidden'));

    // Show the selected view
    if (view === 'books') {
        booksListEl.classList.remove('hidden');
        currentView = 'books';
    } else if (view === 'details') {
        bookDetailsEl.classList.remove('hidden');
        currentView = 'details';
    } else if (view === 'purchased') {
        purchasedBooksEl.classList.remove('hidden');
        currentView = 'purchased';
    }
}

// API Functions
async function fetchBooks() {
    try {
        const response = await fetch(API_ENDPOINTS.BOOKS);
        if (!response.ok) throw new Error('Failed to fetch books');
        return await response.json();
    } catch (error) {
        console.error('Error fetching books:', error);
        showNotification('Failed to load books. Please try again later.', 'error');
        return [];
    }
}

async function fetchBooksById(id) {
    try {
        const response = await fetch(`${API_ENDPOINTS.BOOKS}/${id}`);
        if (!response.ok) throw new Error('Failed to fetch books');
        return await response.json();
    } catch (error) {
        console.error('Error fetching books:', error);
        showNotification('Failed to load books. Please try again later.', 'error');
        return [];
    }
}

async function purchaseBook(bookId) {
    try {
        const response = await fetch(API_ENDPOINTS.PURCHASES, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ bookId })
        });

        if (!response.ok) throw new Error('Purchase failed');

        const result = await response.json();
        showNotification('Book purchased successfully!', 'success');
        return result;
    } catch (error) {
        console.error('Error purchasing book:', error);
        showNotification('Failed to purchase book. Please try again.', 'error');
        throw error;
    }
}

async function fetchPurchasedBooks() {
    try {
        const response = await fetch(API_ENDPOINTS.PURCHASES);
        if (!response.ok) throw new Error('Failed to fetch purchased books');
        return await response.json();
    } catch (error) {
        console.error('Error fetching purchased books:', error);
        showNotification('Failed to load purchased books. Please try again later.', 'error');
        return [];
    }
}

// UI Update Functions
async function loadBooks() {
    const books = await fetchBooks();
    renderBooks(books);
}

function renderBooks(books) {
    if (!books || books.length === 0) {
        booksListEl.innerHTML = '<p>No books available at the moment.</p>';
        return;
    }

    const booksHtml = `
        <div class="books-grid">
            ${books.map(book => `
                <div class="book-card" data-id="${book.id}">
                    <img src="${book.coverImage || 'https://via.placeholder.com/250x300?text=No+Cover'}" alt="${book.title}" class="book-cover">
                    <div class="book-info">
                        <h3>${book.title}</h3>
                        <p>${book.author}</p>
                        <p class="price">$${book.price?.toFixed(2) || 'N/A'}</p>
                    </div>
                </div>
            `).join('')}
        </div>
    `;

    booksListEl.innerHTML = booksHtml;

    // Add click event to book cards
    document.querySelectorAll('.book-card').forEach(card => {
        card.addEventListener('click', () => showBookDetails(card.dataset.id));
    });
}

async function showBookDetails(bookId) {
    const book = await fetchBooksById(bookId);

    if (!book) {
        showNotification('Book not found', 'error');
        return;
    }

    currentBookId = bookId;

    const bookHtml = `
        <div class="book-detail">
            <img src="${book.coverImage || 'https://via.placeholder.com/300x400?text=No+Cover'}" alt="${book.title}">
            <div class="book-detail-info">
                <h2>${book.title}</h2>
                <p class="author">By ${book.author}</p>
                <p class="price">$${book.price?.toFixed(2) || 'N/A'}</p>
                <p class="description">${book.description || 'No description available.'}</p>
                <button class="buy-btn" id="purchaseBtn">Purchase for $${book.price?.toFixed(2) || 'N/A'}</button>
            </div>
        </div>
    `;

    bookInfoEl.innerHTML = bookHtml;
    showView('details');

    // Add event listener to purchase button
    document.getElementById('purchaseBtn')?.addEventListener('click', () => handlePurchase(bookId));
}

async function loadPurchasedBooks() {
    const purchasedBooks = await fetchPurchasedBooks();
    renderPurchasedBooks(purchasedBooks);
}

function renderPurchasedBooks(purchases) {
    if (!purchases || purchases.length === 0) {
        purchasedListEl.innerHTML = '<p>You haven\'t purchased any books yet.</p>';
        return;
    }

    console.log("purchased books", purchases)

    const booksHtml = `
        <div class="books-grid">
            ${purchases.map(p => `
                <div class="book-card" data-id="${p.book.id}">
                    <img src="${p.book.coverImage}" alt="${p.book.title}" class="book-cover">
                    <div class="book-info">
                        <h3>${p.book.title}</h3>
                        <p>${p.book.author}</p>
                        <p>Purchased on: ${new Date(p.purchaseDate).toLocaleDateString()}</p>
                        <P>Order status: ${p.status}</p>
                    </div>
                </div>
            `).join('')}
        </div>
    `;

    purchasedListEl.innerHTML = booksHtml;
}

async function handlePurchase(bookId) {
    try {
        await purchaseBook(bookId);
        // After successful purchase, update the UI
        if (currentView === 'details') {
            showView('purchased');
            loadPurchasedBooks();
        }
    } catch (error) {
        // Error handling is done in the purchaseBook function
    }
}

// Notification function
function showNotification(message, type = 'success') {
    notificationEl.textContent = message;
    notificationEl.className = `notification ${type}`;
    notificationEl.classList.add('show');

    setTimeout(() => {
        notificationEl.classList.remove('show');
    }, 3000);
}
