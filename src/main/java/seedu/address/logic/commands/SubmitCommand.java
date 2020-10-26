package seedu.address.logic.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderItem;

public class SubmitCommand extends Command {

    public static final String COMMAND_WORD = "submit";

    public static final String MESSAGE_ORDER_COPIED_TO_CLIPBOARD = "The order has been copied to the clipboard.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (!model.isSelected()) {
            throw new CommandException(ParserUtil.MESSAGE_VENDOR_NOT_SELECTED);
        }

        Order order = new Order();
        order.setOrderItems(model.getObservableOrderItemList());
        StringBuilder orderText = new StringBuilder();
        for (OrderItem orderItem: order) {
            orderText.append(String.format("%s x %d\n", orderItem.getName(), orderItem.getQuantity()));
        }
        StringSelection stringSelection = new StringSelection(orderText.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        String estimatedTotal = String.format("Estimated total: $%.2f", order.getTotal());

        String displayText = MESSAGE_ORDER_COPIED_TO_CLIPBOARD + '\n' + orderText + estimatedTotal;
        return new CommandResult(displayText);
    }
}
