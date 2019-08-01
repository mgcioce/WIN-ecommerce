# Day 2

## Standup

- What did we learn from day 1
- What is left from day 1 if you didn't finish
- What were your challenges
- Any impediments 

## Planning Meeting

- What are we going to accomplish on day 2?
    - Create strategy for what will be done and how. 

### *__First Break__*

## Review Homework

- Which tests failed?
    - Why?

## Work on it

- Set up Database
    - Models
        - User
            - Create a user model with the following items:
                            - auto generated id
                            - Strings for username and password (they can't be null)
                            - a Map that uses Product and Integer pairs. called "cart" that is implemented by a HashMap
                                - Use the `@ElementCollection` tag: It basically makes the map into a table with a Foreign Key to a Product and the quantity as a number without making a new model. Very similar to @OneToMany
            - This goes at the bottom of the model. 
            - Extend the `UserDetails` class from Spring Security.
            ```java
              	// UserDetails requires these, they are technically getters (is-ers?) overridden by Lombok.
              	// @Transient Makes it so these aren't persisted in the database, as they are hard coded.
              	@Transient
              	private boolean accountNonExpired = true;
              	@Transient
              	private boolean accountNonLocked = true;
              	@Transient
              	private boolean credentialsNonExpired = true;
              	@Transient
              	private boolean enabled = true;
              	@Transient
              	private Collection<GrantedAuthority> authorities = null;

            ```
            
        - Product
            - Create a product model with the following items:
                - id that auto increments. 
                - int quantity
                - price
                - Strings for 
                    - description
                    - name
                    - brand
                    - category
                    - image (url)
    - Repo
        - ProductRepository
            ```java
              @Repository
              public interface ProductRepository extends CrudRepository<Product, Long> {
                  List<Product> findAll();
                  Product findById(long id);
                  List<Product> findByBrand(String brand);
                  List<Product> findByCategory(String category);
                  List<Product> findByBrandAndCategory(String brand, String category);
                  
                  @Query("SELECT DISTINCT p.brand FROM Product p")
                  List<String> findDistinctBrands();
                  
                  @Query("SELECT DISTINCT p.category FROM Product p")
                  List<String> findDistinctCategories();
          }
            ```
          - UserRepository
            ```java
                @Repository
                public interface UserRepository extends CrudRepository<User, Long> {
                	User findByUsername(String username);
                }

            ```
    - Services
        -UserService
            ```java
                @Service
                public class UserService implements UserDetailsService {
                	@Autowired
                	private UserRepository userRepository;
                	@Autowired
                	private BCryptPasswordEncoder bCryptPasswordEncoder;
                	
                	public User findByUsername(String username) {
                		return userRepository.findByUsername(username);
                	}
                	
                	public void saveNew(User user) {
                		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                		userRepository.save(user);
                	}
                	
                	public void saveExisting(User user) {
                		userRepository.save(user);
                	}
                	
                	public User getLoggedInUser() {
                		return findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
                	}
                	
                	public void updateCart(Map<Product, Integer> cart) {
                		User user = getLoggedInUser();
                		user.setCart(cart);
                		saveExisting(user);
                	}
                	
                	@Override
                	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                		User user = findByUsername(username);
                		if(user == null) throw new UsernameNotFoundException("Username not found.");
                		return user;
                	}
                }

            ```
            
        - ProductService
            ```java
              @Service
              public class ProductService {
                  @Autowired
                  private ProductRepository productRepository;
                  
                  public List<Product> findAll() {
                      return productRepository.findAll();
                  }
              
                  public Product findById(long id) {
                      return productRepository.findById(id);
                  }
                  
                  public List<String> findDistinctBrands() {
                      return productRepository.findDistinctBrands();
                  }
                  
                  public List<String> findDistinctCategories() {
                      return productRepository.findDistinctCategories();
                  }
                  
                  public void save(Product product) {
                      productRepository.save(product);
                  }
                  
                  public void deleteById(long id) {
                      productRepository.deleteById(id);
                  }
                  
                  public List<Product> findByBrandAndOrCategory(String brand, String category) {
                      if(category == null && brand == null)
                          return productRepository.findAll();
                      else if(category == null)
                          return productRepository.findByBrand(brand);
                      else if(brand == null)
                          return  productRepository.findByCategory(category);
                      else
                          return productRepository.findByBrandAndCategory(brand, category);
                  }
              }
            ```
- Set up User Authentication
    - SecurityConfiguration
        ```java
          @Configuration
          @EnableWebSecurity
          public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
          	@Autowired
          	private UserService userService;
          	@Autowired
          	private BCryptPasswordEncoder bCryptPasswordEncoder;
          	
          	@Override
          	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
          	}
          	
          	@Override
          	protected void configure(HttpSecurity http) throws Exception {
          		http
          			.authorizeRequests()
          				.antMatchers("/cart").authenticated()
          			.and().formLogin()
          				.loginPage("/signin")
          				.loginProcessingUrl("/login")
          			.and().logout()
          				.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
          				.logoutSuccessUrl("/");
          	}
          }

        ```
      - WebMvcConfiguration
        ```java
            @Configuration
            public class WebMvcConfiguration {
            	@Bean
            	public BCryptPasswordEncoder passwordEncoder() {
            		return new BCryptPasswordEncoder();
            	}
            }

        ```
## Authentication 
- AuthenticationController
    ```java
      @Controller
      class AuthenticationController {
      	@Autowired
      	private UserService userService;
      	
      	@GetMapping("/signin")
      	public String login() {
      		return "signin";
      	}
      	
      	@PostMapping("/signin")
      	public String singup(@Valid User user,
      	                     @RequestParam String submit,
      	                     BindingResult bindingResult,
      	                     HttpServletRequest request) throws ServletException {
      		String password = user.getPassword();
      		if(submit.equals("up")) {
      			if(userService.findByUsername(user.getUsername()) == null) {
      				userService.saveNew(user);
      			} else {
      				bindingResult.rejectValue("username", "error.user", "Username is already taken.");
      				return "signin";
      			}
      		}
      		request.login(user.getUsername(), password);
      		return "redirect:/";
      	}
      }

    ```

### *__Lunch__*

## Cart
- CartController
    ```java
      @Controller
      @ControllerAdvice
      public class CartController {
      	@Autowired
      	ProductService productService;
      	
      	@Autowired
      	UserService userService;
      	
      	@ModelAttribute("loggedInUser")
      	public User loggedInUser() {
      		return userService.getLoggedInUser();
      	}
      	
      	@ModelAttribute("cart")
      	public Map<Product, Integer> cart() {
      		User user = loggedInUser();
      		if(user == null) return null;
      		System.out.println("Getting cart");
      		return user.getCart();
      	}
      	
      	/**
      	 * Puts an empty list in the model that thymeleaf can use to sum up the cart total.
      	 */
      	@ModelAttribute("list")
      	public List<Double> list() {
      		return new ArrayList<>();
      	}
      	
      	@GetMapping("/cart")
      	public String showCart() {
      		return "cart";
      	}
      	
      	@PostMapping("/cart")
      	public String addToCart(@RequestParam long id) {
      		Product p = productService.findById(id);
      		setQuantity(p, cart().getOrDefault(p, 0) + 1);
      		return "cart";
      	}
      	
      	@PatchMapping("/cart")
      	public String updateQuantities(@RequestParam long[] id, @RequestParam int[] quantity) {
      		for(int i = 0; i < id.length; i++) {
      			Product p = productService.findById(id[i]);
      			setQuantity(p, quantity[i]);
      		}
      		return "cart";
      	}
      	
      	@DeleteMapping("/cart")
      	public String removeFromCart(@RequestParam long id) {
      		Product p = productService.findById(id);
      		setQuantity(p, 0);
      		return "cart";
      	}
      	
      	private void setQuantity(Product p, int quantity) {
      		if(quantity > 0)
      			cart().put(p, quantity);
      		else
      			cart().remove(p);
      		
      		userService.updateCart(cart());
      	}
      }

    ```

## Dynamic Products List

- Set up Dynamic Products
    - In the main.html file, make the static list of products pull from the database and list them out with just a single block. Rather than having 4 cards. 
    
## Finish all extraneous routing
- Link all the HTML pages together
    - Put in the appropriate links for the pages to work together.
    - Use the test files to help you determine that all routes work.

# Retrospective
   - What did we learn
   - Any improvements going forward
   - Did everyone get it done 
   - etc.

# *__HomeWork:__*
- Do JUnit testing
- Do integration & regression testing
