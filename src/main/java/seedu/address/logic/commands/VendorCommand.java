package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.vendor.Vendor;

/**
 * Selects a Vendor to order from.
 */
public class VendorCommand extends Command {

    public static final String COMMAND_WORD = "vendor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects a Vendor in the AddressBook. "
            + "Parameters: "
            + " Index of Vendor";

    public static final String MESSAGE_SELECT_VENDOR_SUCCESS = "Vendor %s has been selected.";

    private final Index vendorIndex;

    /**
     * Creates a VendorCommand to select the vendor at the specified {@code index} of the AddressBook
     */
    public VendorCommand(Index index) {
        requireNonNull(index);
        this.vendorIndex = index;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Vendor> vendors = model.getFilteredVendorList();
        int index = vendorIndex.getZeroBased();

        if (vendors.size() <= index) {
            throw new CommandException(ParserUtil.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
        }

        int oldIndex = model.getVendorIndex();
        model.setVendorIndex(index);

        if (oldIndex != index) {
            // TODO: Assign new orderManager (otherwise undo will get back previous order)
            model.clearOrder();
        }

        return new CommandResult(String.format(MESSAGE_SELECT_VENDOR_SUCCESS, vendorIndex.getOneBased()),
                false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VendorCommand // instanceof handles nulls
                && vendorIndex.equals(((VendorCommand) other).vendorIndex));
    }

}
