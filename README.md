# modulo-game
Home work project for Software Engineering
## Rules:
First, you have to add a so-called "module" number, k.
You can move left, right, up or down with the WASD buttons or with the arrow keys.
Your current position show you how many steps you must go.

When you arrive your new position, its value will be added to each of the eigth adjacent field's value.
If their new value (n) is greater than k-1, then its new value will be n mod k.

That means if a field's new value would be exactly k, than it will become k mod k = 0.
The goal is to reach one of the fields which value is 0, in the fewest steps.

You can't "leave" the table because once you reach a field which is in the edge of the table and you still have some steps
then you can continue at the beginning of the row (if the direction is right), etc.

You can apply the same rule when your new position is not surrounded directly with adjacent fields.
