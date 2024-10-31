package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dto.Book;

public class BookRepository {
	//����� ����
	//Book ��ü ����
	//private static ArrayList<Book> listOfBooks = new ArrayList<Book>();
	//�̱���
	private static BookRepository repository = new BookRepository();
	//�̱���
	public static BookRepository getInstance() {
		System.out.println("2: BookRepository ��ü�� �����ϱ� ���� getInstance ����");
		return repository;
	}
	//������
	public BookRepository() {}
	
	public Connection dbconn(){
		System.out.println("[������ ���̽� ���� �Լ� dbconn() ȣ��]");
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String db = "jdbc:mysql://localhost:3306/BookMarket_CRUD";
			String id = "root";
			String pwd = "1234";
			conn = DriverManager.getConnection(db, id, pwd);
			System.out.println("db���Ἲ��");
		} catch (Exception e) {
			System.out.println("db�������");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	//��ü å DTO�� ArrayList�� ��� ����
	public ArrayList<Book> getAllBooks(){
		System.out.println("[3: Repository�� getAllBooksȣ��]");
		Connection conn = dbconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Book> listOfBooks = new ArrayList<Book>();
		try {
			//select * from book
			String sql = "select * from book";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			//��ó��
			while(rs.next()) {
				//DTO�� ���� �̸�, Repository ���� �̸�, DB �÷� �̸�
				//���� < DTO < ArrayList<DTO>
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
				
				System.out.println("DTO�� bookId : "+bookId);
				
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
				System.out.println("rs����");
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				System.out.println("pstmt����");
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				System.out.println("Connection ����");
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
	
	//�ϳ��� å DTO�� �����Ѵ�.
	public void addBook(Book book) {
		System.out.println("[å�� ����ϱ� ���� BookRepository�� addBook() ȣ��]");
		//������ ���̽� ����
		Connection conn = dbconn();
		System.out.println("������ ���̽� ���� �Ϸ�");
		//SQL ����			
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
			System.out.println("å�� ����մϴ�. "+pstmt);
			pstmt.executeUpdate();
			System.out.println("��� ����");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//���ܰ� �߻��ص� close()�� �ݵ�� ����Ǿ���
			if(pstmt!=null) {
				System.out.println("pstmt �ݱ�");
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				System.out.println("conn�ݱ�");
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	//�ϳ��� å DTO�� �����Ѵ�.
	public Book getBookById(String bookId) {
		System.out.println("["+bookId+"�� �ش��ϴ� DTO�� �������� ���� getBookById ȣ��]");
		Book bk = new Book();;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		//�����ͺ��̽� ����
		Connection conn = dbconn();
		try {
			//SQL����
			sql = "select * from book where bookId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookId);
			rs = pstmt.executeQuery();
			//ResultSet�� ��ü�� ��ȯ
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
				System.out.println("rs����");
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				System.out.println("pstmt����");
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				System.out.println("Connection ����");
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
	
	//�ϳ��� å DTO�� �����Ѵ�.
	public void delBook(String bookId) {
		System.out.println("[åDTO�� DB���� �����ϱ� ���� delBook�Լ� ȣ��]");
		//�����ͺ��̽� ����
		Connection conn = dbconn();
		PreparedStatement pstmt = null;
		String sql = null;
		System.out.println("�����ͺ��̽� ���� �Ϸ�");
		try {
			//sql����
			sql = "delete from book where bookId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookId);
			System.out.println(pstmt);
			pstmt.executeUpdate();
			System.out.println(bookId+"�� ���Ե� DTO���� �Ϸ�");
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
	
	//�ϳ��� å DTO�� �����Ѵ�.
	public void updBook(Book bk){
		System.out.println("["+bk.getBookId()+"�� ���Ե� DTO�� ������Ʈ �ϱ� ���� updBook() �Լ� ȣ��]");
		//DB����
		Connection conn = dbconn();
		System.out.println("�����ͺ��̽� ���� �Ϸ�");
		PreparedStatement pstmt=null;
		try {
			//SQL ����
			//update book set id=?,bookname=?,unitPrice=?, author=?, bookdescription=?, publisher=?, category=?, unitsInStock=?, releaseDate=?, bookcondition=?, filename=?
			String sql = null;
			String filename = bk.getFilename();
			if(filename!=null) {
				System.out.println("(if)file�� �߰����� �� ������ ������ ��� �÷��� ������Ʈ");
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
				System.out.println("(if)file�� �߰����� �ʾ��� �� ������ ���� ��� �÷��� ������Ʈ");
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
			System.out.println("������Ʈ �Ϸ�");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					System.out.println("pstmt����");
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					System.out.println("conn����");
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
				System.out.println("rs����");
				rs.close();
			}
			if(pstmt!=null) {
				System.out.println("pstmt����");
				pstmt.close();
			}
			if(conn!=null) {
				System.out.println("Connection ����");
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
				System.out.println("rs����");
				rs.close();
			}
			if(pstmt!=null) {
				System.out.println("pstmt����");
				pstmt.close();
			}
			if(conn!=null) {
				System.out.println("Connection ����");
				conn.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return book;
	}
	
	public void createBook(Book book){
		System.out.println("createBook�Լ� ȣ��");
		//�ε�, Connection ����
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
			System.out.println("å�� ����մϴ�.");
			pstmt.executeUpdate();
			
			if(pstmt!=null) {
				System.out.println("pstmt �ݱ�");
				pstmt.close();
			}
			if(conn!=null) {
				System.out.println("conn�ݱ�");
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
			System.out.println("������Ʈ �Ϸ�");
			
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