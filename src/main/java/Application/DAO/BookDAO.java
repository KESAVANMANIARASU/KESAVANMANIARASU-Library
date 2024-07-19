package Application.DAO;

import Application.Util.ConnectionUtil;
import Application.Model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO is a class that mediates the transformation of data between the format of objects in Java to rows in a
 * database. The methods here are mostly filled out, you will just need to add a SQL statement.
 *
 * We may assume that the database has already created a table named 'book'.
 * It contains similar values as the Author class:
 * isbn, which is of type int and is a primary key,
 * author_id, which is of type int, and is a foreign key associated with the column 'id' of 'author',
 * title, which is of type varchar(255),
 * copies_available, which is of type int.
 */
public class BookDAO {
    /**
     * Retrieve all books from the Book table.
     * @return all Books.
     */
    public List<Book> getAllBooks(){
        Connection connection = ConnectionUtil.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            // SQL logic to retrieve all books
            String sql = "SELECT * FROM book";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Book book = new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                books.add(book);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return books;
    }

    /**
     * Retrieve a book from the Book table, identified by its isbn.
     * @param isbn the ISBN of the book to retrieve.
     * @return a book identified by isbn.
     */
    public Book getBookByIsbn(int isbn){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL logic to retrieve a book by ISBN
            String sql = "SELECT * FROM book WHERE isbn = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Set the ISBN parameter
            preparedStatement.setInt(1, isbn);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                Book book = new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                return book;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Insert a book into the Book table.
     * @param book the Book object to insert.
     * @return the inserted Book object.
     */
    public Book insertBook(Book book){
        Connection connection = ConnectionUtil.getConnection();
        try {
            // SQL logic to insert a book
            String sql = "INSERT INTO book (isbn, author_id, title, copies_available) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Set the parameters
            preparedStatement.setInt(1, book.getIsbn());
            preparedStatement.setInt(2, book.getAuthor_id());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setInt(4, book.getCopies_available());

            preparedStatement.executeUpdate();
            return book;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Retrieve all books from the Book table with copies_available over zero.
     * @return all books with book count > 0.
     */
    public List<Book> getBooksWithBookCountOverZero(){
        Connection connection = ConnectionUtil.getConnection();
        List<Book> books = new ArrayList<>();
        try {
            // SQL logic to retrieve books with copies available over zero
            String sql = "SELECT * FROM book WHERE copies_available > 0";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Book book = new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                books.add(book);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return books;
    }
}
