# ScreenX


-> ROOM DB for localling storing Reviews with Coroutines


-> MVVM components and DataBinding


-> Kotlin


-> Dynamic Views 


## Use Case :

1) On pressing add review, an alert should open 

2) Alert should have fields like - name, comment, rating count, number of images(int - based on the count the number of images should be displayed inside horizontal list) and button to post review

3) Once the review is posted it should display in the review list.

4) User should have the ability to delete the review or edit the review.

5) Load more button should work as pagination, 5 items at a time.

6) The horizontal image list should be hidden initially on click particular review it should expand and when click again it should hide.

## Screenshots 

![Screenshot_1641790827](https://user-images.githubusercontent.com/77268176/148722227-32466580-37b2-48e1-9aaa-baf8ae410b74.png)


![Screenshot_1641794575](https://user-images.githubusercontent.com/77268176/148724023-0c739e13-7b1c-420b-99c7-f3460d2cc830.png) 

The list is an header recycler view. Under each heading there can be n number of items

Flow should be like below

1) Initially there should not be any checkbox, select all option, disable this item button

2) on long pressing to any item initially, the selection mode will be enabled, during this time the above options should be visible. During selection mode, next to select all place × button to remove selection mode. (basically how we perform select option in other apps)

3) On pressing select all, all the items should be selected, on deselecting all the items should be deselected.

4) After pressing select all, if any one item is deselected, then select all check box should be deselected.

5) on pressing disable item, a toast should be visible which has all the selected item names.



## Screenshots Disabling Products 

![Screenshot_1642528012](https://user-images.githubusercontent.com/77268176/149991274-9ff36360-6ff0-4a0c-a40b-8a178b1819fe.png)


![Screenshot_1642528027](https://user-images.githubusercontent.com/77268176/149991284-8ac21406-9417-4dc6-afae-671eeb160a88.png)


![Screenshot_1642528404](https://user-images.githubusercontent.com/77268176/149992004-2b46f04f-d87f-4dfb-b0a6-51bac9a73be7.png)
