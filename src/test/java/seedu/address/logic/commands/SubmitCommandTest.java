package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.food.Food;
import seedu.address.model.order.OrderItem;
import seedu.address.testutil.TypicalModel;

public class SubmitCommandTest {

    @Test
    public void execute_submit_success() {
        Model model = TypicalModel.getModelManagerWithMenu();
        Model expectedModel = TypicalModel.getModelManagerWithMenu();
        ObservableList<Food> menu = model.getFilteredFoodList();

        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append(SubmitCommand.MESSAGE_ORDER_COPIED_TO_CLIPBOARD + '\n');
        double calculatedTotal = 0;
        for (int i = 0; i < 5; i++) {
            OrderItem orderItem = new OrderItem(menu.get(i), i + 6);
            expectedMessage.append(String.format("%s x %d\n", orderItem.getName(), i + 6));
            calculatedTotal += orderItem.getPrice() * (i + 6);
            model.addOrderItem(orderItem);
            expectedModel.addOrderItem(orderItem);
        }
        expectedMessage.append(String.format("Estimated total: $%.2f", calculatedTotal));
        assertCommandSuccess(new SubmitCommand(), model, expectedMessage.toString(), expectedModel);
    }
}
