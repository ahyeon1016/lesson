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
	//Book 객체 묶음
	//private static ArrayList<Book> listOfBooks = new ArrayList<Book>();
	//싱글톤
	private static BookRepository repository = new BookRepository();
	//싱글톤
	public static BookRepository getInstance() {
		System.out.println("2: BookRepository 객체를 전달하기 위한 getInstance 실행");
		return repository;
	}
	//생성자
	public BookRepository() {}
	
	public Connection dbconn(){
		System.out.println("[데이터 베이스 생성 함수 dbconn() 호출]");
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String db = "jdbc:mysql://localhost:3306/BookMarket_CRUD";
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
	
	//전체 책 DTO를 ArrayList에 묶어서 전송
	public ArrayList<Book> getAllBooks(){
		System.out.println("[3: Repository의 getAllBooks호출]");
		Connection conn = dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Book> listOfBooks = new ArrayList<Book>();
		try {
			//select * from book
			String sql = "select * from book";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//후처리
			while(rs.next()) {
				//DTO의 변수 이름, Repository 변수 이름, DB 컬럼 이름
				//변수 < DTO < ArrayList<DTO>
				String bookId = rs.getString("bookId");
				String bookname = rs.getString("bookname");
				int unitPrice = rs.getInt("unitPrice");
				String author = rs.getString("author");
				String bookdescription = rs.getString("bookdescription");
				String publisher = rs.getString("publisher");
				String category = rs.getString("category");
				long unitsInStock = rs.getLong("unitsInStock");
				String releaseDate = rs.getString("releaseDate");
				String bookcondition = rs.getString("bookcondition");
				String filename = rs.getString("filename");
				
				System.out.println("DTO의 bookId : "+bookId);
				
				Book bk = new Book();
				
				bk.setBookId(bookId);
				bk.setBookname(bookname);
				bk.setUnitPrice(unitPrice);
				bk.setAuthor(author);
				bk.setBookdescription(bookdescription);
				bk.setPublisher(publisher);
				bk.setCategory(category);
				bk.setUnitsInStock(unitsInStock);
				bk.setReleaseDate(releaseDate);
				bk.setBookcondition(bookcondition);
				bk.setFilename(filename);
				
				listOfBooks.add(bk);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				System.out.println("rs닫음");
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				System.out.println("pstmt닫음");
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				System.out.println("Connection 닫음");
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return listOfBooks;
	}
	
	//하나의 책 DTO를 삽입한다.
	public void addBook(Book book) {
		System.out.println("[책을 등록하기 위해 BookRepository의 addBook() 호출]");
		//데이터 베이스 연결
		Connection conn = dbconn();
		System.out.println("데이터 베이스 연결 완료");
		//SQL 전송			
		String sql = null;
		PreparedStatement pstmt = null;
		try {
			//insert into book values(?,?,?,?,?,?,?,?,?,?,?)
			sql = "insert into book values(?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, book.getBookId());
			pstmt.setString(2, book.getBookname());
			pstmt.setInt(3, book.getUnitPrice());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getBookdescription());
			pstmt.setString(6, book.getPublisher());
			pstmt.setString(7, book.getCategory());
			pstmt.setLong(8, book.getUnitsInStock());
			pstmt.setString(9, book.getReleaseDate());
			pstmt.setString(10, book.getBookcondition());
			pstmt.setString(11, book.getFilename());
			System.out.println("책을 등록합니다. "+pstmt);
			pstmt.executeUpdate();
			System.out.println("등록 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//예외가 발생해도 close()는 반드시 실행되야함
			if(pstmt!=null) {
				System.out.println("pstmt 닫기");
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				System.out.println("conn닫기");
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	//하나의 책 DTO를 리턴한다.
	public Book getBookById(String bookId) {
		System.out.println("["+bookId+"에 해당하는 DTO를 가져오기 위해 getBookById 호출]");
		Book bk = new Book();;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		//데이터베이스 연결
		Connection conn = dbconn();
		try {
			//SQL전송
			sql = "select * from book where bookId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookId);
			rs = pstmt.executeQuery();
			//ResultSet을 객체로 전환
			if(rs.next()) {
				String dbbookId = rs.getString("bookId");
				String bookname = rs.getString("bookname");
				int unitPrice = rs.getInt("unitPrice");
				String author = rs.getString("author");
				String bookdescription = rs.getString("bookdescription");
				String publisher = rs.getString("publisher");
				String category = rs.getString("category");
				long unitsInStock = rs.getLong("unitsInStock");
				String releaseDate = rs.getString("releaseDate");
				String bookcondition = rs.getString("bookcondition");
				String filename = rs.getString("filename");
				
				System.out.println(dbbookId);
				
				bk.setBookId(dbbookId);
				bk.setBookname(bookname);
				bk.setUnitPrice(unitPrice);
				bk.setAuthor(author);
				bk.setBookdescription(bookdescription);
				bk.setPublisher(publisher);
				bk.setCategory(category);
				bk.setUnitsInStock(unitsInStock);
				bk.setReleaseDate(releaseDate);
				bk.setBookcondition(bookcondition);
				bk.setFilename(filename);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
			
			if(rs!=null) {
				System.out.println("rs닫음");
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				System.out.println("pstmt닫음");
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				System.out.println("Connection 닫음");
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return bk;
	}
	
	//하나의 책 DTO를 삭제한다.
	public void delBook(String bookId) {
		System.out.println("[책DTO를 DB에서 제거하기 위해 delBook함수 호출]");
		//데이터베이스 연결
		Connection conn = dbconn();
		PreparedStatement pstmt = null;
		String sql = null;
		System.out.println("데이터베이스 연결 완료");
		try {
			//sql전송
			sql = "delete from book where bookId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookId);
			System.out.println(pstmt);
			pstmt.executeUpdate();
			System.out.println(bookId+"가 포함된 DTO제거 완료");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//하나의 책 DTO를 수정한다.
	public void updBook(Book bk){
		System.out.println("["+bk.getBookId()+"가 포함된 DTO를 업데이트 하기 위한 updBook() 함수 호출]");
		//DB연결
		Connection conn = dbconn();
		System.out.println("데이터베이스 연결 완료");
		PreparedStatement pstmt=null;
		try {
			//SQL 전송
			//update book set id=?,bookname=?,unitPrice=?, author=?, bookdescription=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, bookcondition=?, filename=?
			String sql = null;
			String filename = bk.getFilename();
			if(filename!=null) {
				System.out.println("(if)file을 추가했을 때 파일을 포함한 모든 컬럼을 업데이트");
				sql = "update book set bookname=?,unitPrice=?, author=?, bookdescription=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, bookcondition=?, filename=? where bookId=?";
				pstmt = conn.prepareStatement(sql);
				System.out.println(bk.getBookId());
				pstmt.setString(1, bk.getBookname());
				pstmt.setInt(2, bk.getUnitPrice());
				pstmt.setString(3, bk.getAuthor());
				pstmt.setString(4, bk.getBookdescription());
				pstmt.setString(5, bk.getPublisher());
				pstmt.setString(6, bk.getCategory());
				pstmt.setLong(7, bk.getUnitsInStock());
				pstmt.setString(8, bk.getReleaseDate());
				pstmt.setString(9, bk.getBookcondition());
				pstmt.setString(10, bk.getFilename());
				pstmt.setString(11, bk.getBookId());
			} else {
				System.out.println("(if)file을 추가하지 않았을 때 파일을 제외 모든 컬럼을 업데이트");
				sql = "UPDATE book SET bookname=?, unitPrice=?, author=?, bookdescription=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, bookcondition=? WHERE bookId=?";   
				pstmt = conn.prepareStatement(sql);
				System.out.println(bk.getBookId());
				pstmt.setString(1, bk.getBookname());
				pstmt.setInt(2, bk.getUnitPrice());
				pstmt.setString(3, bk.getAuthor());
				pstmt.setString(4, bk.getBookdescription());
				pstmt.setString(5, bk.getPublisher());
				pstmt.setString(6, bk.getCategory());
				pstmt.setLong(7, bk.getUnitsInStock());
				pstmt.setString(8, bk.getReleaseDate());
				pstmt.setString(9, bk.getBookcondition());
				pstmt.setString(10, bk.getBookId());
			}
			
			System.out.println(pstmt);
			pstmt.executeUpdate();
			System.out.println("업데이트 완료");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					System.out.println("pstmt닫음");
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					System.out.println("conn닫음");
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public ArrayList<Book> readBook(){
		ArrayList<Book> arr = new ArrayList<Book>();
		Connection conn = dbconn();
		try {
			//select * from book
			String sql = "select * from book";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String bookId = rs.getString("bookId");
				String bookname = rs.getString("bookname");
				int unitPrice = rs.getInt("unitPrice");
				String author = rs.getString("author");
				String bookdescription = rs.getString("bookdescription");
				String publisher = rs.getString("publisher");
				String category = rs.getString("category");
				long unitsInStock = rs.getLong("unitsInStock");
				String releaseDate = rs.getString("releaseDate");
				String bookcondition = rs.getString("bookcondition");
				String filename = rs.getString("filename");
				
				System.out.println(bookId);
				
				Book book = new Book();
				
				book.setBookId(bookId);
				book.setBookname(bookname);
				book.setUnitPrice(unitPrice);
				book.setAuthor(author);
				book.setBookdescription(bookdescription);
				book.setPublisher(publisher);
				book.setCategory(category);
				book.setUnitsInStock(unitsInStock);
				book.setReleaseDate(releaseDate);
				book.setBookcondition(bookcondition);
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
				String bookname = rs.getString("bookname");
				int unitPrice = rs.getInt("unitPrice");
				String author = rs.getString("author");
				String bookdescription = rs.getString("bookdescription");
				String publisher = rs.getString("publisher");
				String category = rs.getString("category");
				long unitsInStock = rs.getLong("unitsInStock");
				String releaseDate = rs.getString("releaseDate");
				String bookcondition = rs.getString("bookcondition");
				String filename = rs.getString("filename");
				
				System.out.println(bookId);
				
				
				
				book.setBookId(bookId);
				book.setBookname(bookname);
				book.setUnitPrice(unitPrice);
				book.setAuthor(author);
				book.setBookdescription(bookdescription);
				book.setPublisher(publisher);
				book.setCategory(category);
				book.setUnitsInStock(unitsInStock);
				book.setReleaseDate(releaseDate);
				book.setBookcondition(bookcondition);
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
			pstmt.setString(2, book.getBookname());
			pstmt.setInt(3, book.getUnitPrice());
			pstmt.setString(4, book.getAuthor());
			pstmt.setString(5, book.getBookdescription());
			pstmt.setString(6, book.getPublisher());
			pstmt.setString(7, book.getCategory());
			pstmt.setLong(8, book.getUnitsInStock());
			pstmt.setString(9, book.getReleaseDate());
			pstmt.setString(10, book.getBookcondition());
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
			//update book set id=?,bookname=?,unitPrice=?, author=?, bookdescription=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, bookcondition=?, filename=?
			String sql = "update book set name=?,unitPrice=?, author=?, description=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, condition1=?, filename=? where bookId=?";
			pstmt = conn.prepareStatement(sql);
			System.out.println(book.getBookId());
			pstmt.setString(1, book.getBookname());
			pstmt.setInt(2, book.getUnitPrice());
			pstmt.setString(3, book.getAuthor());
			pstmt.setString(4, book.getBookdescription());
			pstmt.setString(5, book.getPublisher());
			pstmt.setString(6, book.getCategory());
			pstmt.setLong(7, book.getUnitsInStock());
			pstmt.setString(8, book.getReleaseDate());
			pstmt.setString(9, book.getBookcondition());
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
