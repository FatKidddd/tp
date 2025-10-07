package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPlayerDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Player;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPlayerDescriptor objects.
 */
public class EditPlayerDescriptorBuilder {

    private EditPlayerDescriptor descriptor;

    public EditPlayerDescriptorBuilder() {
        descriptor = new EditPlayerDescriptor();
    }

    public EditPlayerDescriptorBuilder(EditPlayerDescriptor descriptor) {
        this.descriptor = new EditPlayerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPlayerDescriptor} with fields containing {@code person}'s details
     */
    public EditPlayerDescriptorBuilder(Player person) {
        descriptor = new EditPlayerDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPlayerDescriptor} that we are building.
     */
    public EditPlayerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPlayerDescriptor}
     * that we are building.
     */
    public EditPlayerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPlayerDescriptor build() {
        return descriptor;
    }
}
