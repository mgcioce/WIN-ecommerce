# Day 3

## Finish Day 2

### *__First Break__*

- Stand Up
- Review Homework
- Planning Meeting

*__Lunch__*

## Stripe API:

https://www.baeldung.com/java-stripe-api

- Dependency
    ```xml
      <dependency>
          <groupId>com.stripe</groupId>
          <artifactId>stripe-java</artifactId>
          <version>4.2.0</version>
      </dependency>
    ```

## How to set environment variables in IntelliJ
​
* Open the dropdown left of the run button and click `Edit configurations...`
![](https://i.imgur.com/BI1sIv6.png)
* Make sure your eCommerce application is selected on the left.
* Click `Environment` to open its fieldset.
![](https://i.imgur.com/Lb3A2DU.png)
* Find the environment variables text box and click the button on its right.
![](https://i.imgur.com/kG2ftTz.png)
* Press the plus button, type the variable's name, press tab and type or paste the value.
![](https://i.imgur.com/BkQjdHG.png)
* Ok, Apply, close

- Add the Pay with stripe button to the checkout page

- When the checkout is done
    ```java
        //use this at the top
        @Autowired
        UserService userService;
        //put this at the end of the post request just before the return.
        userService.getLoggedInUser().getCart().clear();
    ```
    
- Follow the guide in the link above to implement Stripe API with your site
    - Test that it works with the fake card: 4242 4242 4242 4242 exp: 01/20 CVC: 111

- Discuss style guide
- Finalize Project
    - Full test of the entire site
