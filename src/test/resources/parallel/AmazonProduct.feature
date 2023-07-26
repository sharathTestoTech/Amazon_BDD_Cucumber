Feature: Search product and select and add to cart with other functionalities
Scenario: Adding product to the cart and validating accordingly chrome
Given user is on application
Then user search the product "Amazon Essentials Women's Digital Chronograph Resin Strap Watch"  
Then product add to the cart 
And user validates the product with quantity "1" in the cart 
Then user search the product "Mouse" 
And increase the quantity of the product by 2 in cart 
Then product add to the cart 
And user validates the product with quantity "2" in the cart 
Then user search the product "Apple AirTag Leather Key Ring - California Poppy" 
Then product add to the cart 
And user validates the product with quantity "1" in the cart 
Then user deletes the product "Apple AirTag Leather Key Ring - California Poppy" 
Then user search the product "office chair" 
Then product add to the cart 
Then navigate to cart and validate the save later functionality 


