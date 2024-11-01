package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dto.Book;

public class BookRepository {
	//저장소 변수
	private static ArrayList<Book> listOfBooks = new ArrayList<Book>();
	private static BookRepository repository = new BookRepository();
	
	public BookRepository() {
		Book book1 = new Book("ISBN1234", "C# 프로그래밍", 27000);
		book1.setAuther("우재남");
		book1.setDescription("C#을 처음 접하는 독자를 대사응로 일대일 수업처럼 자세히 설명한 책이다. 꼭 알아야 할 핵심 개념은 기본 예제로 최대한 쉽게 설명했으며, 중요한 내용은 응요 예제, 퀴스, 셀프 스터디, 예제 모음으로 한번 더 복습할 수 있다.");
		book1.setPublisher("한빛 아카데미");
		book1.setCategory("IT 모바일");
		book1.setUnitsInStock(1000);
		book1.setReleaseDate("2022/10/06");
		book1.setFilename("cccc.jpg");
		
		Book book2 = new Book("ISBN1235", "자바 프로그래밍", 30000);
		book2.setAuther("송미영");
		book2.setDescription("java를 처음 접하는 독자를 대사응로 일대일 수업처럼 자세히 설명한 책이다. 꼭 알아야 할 핵심 개념은 기본 예제로 최대한 쉽게 설명했으며, 중요한 내용은 응요 예제, 퀴스, 셀프 스터디, 예제 모음으로 한번 더 복습할 수 있다.");
		book2.setPublisher("한빛 아카데미");
		book2.setCategory("IT 모바일");
		book2.setUnitsInStock(1000);
		book2.setReleaseDate("2023/01/01");
		book2.setFilename("java.jpg");
		
		Book book3 = new Book("ISBN1236", "파이썬 프로그래밍", 30000);
		book3.setAuther("최성철");
		book3.setDescription("파이썬을 처음 접하는 독자를 대사응로 일대일 수업처럼 자세히 설명한 책이다. 꼭 알아야 할 핵심 개념은 기본 예제로 최대한 쉽게 설명했으며, 중요한 내용은 응요 예제, 퀴스, 셀프 스터디, 예제 모음으로 한번 더 복습할 수 있다.");
		book3.setPublisher("한빛 아카데미");
		book3.setCategory("IT 모바일");
		book3.setUnitsInStock(1000);
		book3.setReleaseDate("2023/01/01");
		book3.setFilename("python.jpg");
		
		listOfBooks.add(book1);
		listOfBooks.add(book2);
		listOfBooks.add(book3);
	}
	
	public static ArrayList<Book> getAllBooks(){
		return listOfBooks;
	}
	public static BookRepository getInstance() {
		return repository;
	}
	
	public void addBook(Book book) {
		listOfBooks.add(book);
	}
	
	public Book getBookById(String bookId) {
		Book bookById = null;
		
		for(int i=0; i<listOfBooks.size(); i++) {
			Book book = listOfBooks.get(i);
			if(book!=null && book.getBookId()!=null && book.getBookId().equals(bookId)) {
				bookById=book;
				break;
			}
		}
		return bookById;
	}
	
	public Connection dbconn(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String db = "jdbc:mysql://localhost:3306/JSPBook";
			String id = "root";
			String pwd = "1234";
			conn = DriverManager.getConnection(db, id, pwd);
			System.out.println("db연결성공");
		} catch (Exception e) {
			System.out.println("db연결실패");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public ArrayList<Book> readBook(){
		ArrayList<Book> arr = new ArrayList<Book>();
		Connection conn = dbconn();
		//select * from book
		try {
			String sql = "select * from book";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String bookId = rs.getString("bookId");
				String name = rs.getString("name");
				int unitPrice = rs.getInt("unitPrice");
				String author = rs.getString("author");
				String description = rs.getString("description");
				String publisher = rs.getString("publisher");
				String category = rs.getString("category");
				long unitsInStock = rs.getLong("unitsInStock");
				String releaseDate = rs.getString("releaseDate");
				String condition = rs.getString("condition1");
				String filename = rs.getString("filename");
				
				System.out.println(bookId);
				
				Book book = new Book();
				
				book.setBookId(bookId);
				book.setName(name);
				book.setUnitPrice(unitPrice);
				book.setAuther(author);
				book.setDescription(description);
				book.setPublisher(publisher);
				book.setCategory(category);
				book.setUnitsInStock(unitsInStock);
				book.setReleaseDate(releaseDate);
				book.setCondition(condition);
				book.setFilename(filename);
				
				arr.add(book);
			}
			
			if(rs!=null) {
				System.out.println("rs닫음");
				rs.close();
			}
			if(pstmt!=null) {
				System.out.println("pstmt닫음");
				pstmt.close();
			}
			if(conn!=null) {
				System.out.println("Connection 닫음");
				conn.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arr;
	}
	
	public Book readOneBook(String id){
		Book book = new Book();
		Connection conn = dbconn();
		//select * from book
		try {
			String sql = "select * from book where bookId=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String bookId = rs.getString("bookId");
				String name = rs.getString("name");
				int unitPrice = rs.getInt("unitPrice");
				String author = rs.getString("author");
				String description = rs.getString("description");
				String publisher = rs.getString("publisher");
				String category = rs.getString("category");
				long unitsInStock = rs.getLong("unitsInStock");
				String releaseDate = rs.getString("releaseDate");
				String condition = rs.getString("condition1");
				String filename = rs.getString("filename");
				
				System.out.println(bookId);
				
				
				
				book.setBookId(bookId);
				book.setName(name);
				book.setUnitPrice(unitPrice);
				book.setAuther(author);
				book.setDescription(description);
				book.setPublisher(publisher);
				book.setCategory(category);
				book.setUnitsInStock(unitsInStock);
				book.setReleaseDate(releaseDate);
				book.setCondition(condition);
				book.setFilename(filename);
				
			}
			
			if(rs!=null) {
				System.out.println("rs닫음");
				rs.close();
			}
			if(pstmt!=null) {
				System.out.println("pstmt닫음");
				pstmt.close();
			}
			if(conn!=null) {
				System.out.println("Connection 닫음");
				conn.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}
	
	public void createBook(Book book){
		System.out.println("createBook함수 호출");
		//로딩, Connection 생성
		Connection conn = dbconn();
		try {
			//Statement
			//insert into book values(?,?,?,?,?,?,?,?,?,?,?)
			String sql = "insert into book values(?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getBookId());
			pstmt.setString(2, book.getName());
			pstmt.setInt(3, book.getUnitPrice());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getDescription());
			pstmt.setString(6, book.getPublisher());
			pstmt.setString(7, book.getCategory());
			pstmt.setLong(8, book.getUnitsInStock());
			pstmt.setString(9, book.getReleaseDate());
			pstmt.setString(10, book.getCondition());
			pstmt.setString(11, book.getFilename());
			System.out.println("책을 등록합니다.");
			pstmt.executeUpdate();
			
			if(pstmt!=null) {
				System.out.println("pstmt 닫기");
				pstmt.close();
			}
			if(conn!=null) {
				System.out.println("conn닫기");
				conn.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateBook(Book book){
		Connection conn = dbconn();
		
		PreparedStatement pstmt=null;
		try {
			//update book set id=?,name=?,unitPrice=?, author=?, description=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, condition1=?, filename=?
			String sql = "update book set name=?,unitPrice=?, author=?, description=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, condition1=?, filename=? where bookId=?";
			pstmt = conn.prepareStatement(sql);
			System.out.println(book.getBookId());
			pstmt.setString(1, book.getName());
			pstmt.setInt(2, book.getUnitPrice());
			pstmt.setString(3, book.getAuthor());
			pstmt.setString(4, book.getDescription());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getCategory());
			pstmt.setLong(7, book.getUnitsInStock());
			pstmt.setString(8, book.getReleaseDate());
			pstmt.setString(9, book.getCondition());
			pstmt.setString(10, book.getFilename());
			pstmt.setString(11, book.getBookId());
			
			System.out.println(pstmt);
			pstmt.executeUpdate();
			System.out.println("업데이트 완료");
			
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public void deleteBook(String id){
		Connection conn = dbconn();
		try {
			String sql = "delete from book where bookId=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			System.out.println(pstmt);
			pstmt.executeUpdate();
			
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
