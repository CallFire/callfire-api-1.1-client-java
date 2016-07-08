package com.callfire.api11.client;

import com.callfire.api11.client.api.broadcasts.model.Broadcast;
import com.callfire.api11.client.api.broadcasts.model.BroadcastSchedule;
import com.callfire.api11.client.api.broadcasts.model.BroadcastStats;
import com.callfire.api11.client.api.broadcasts.model.ContactBatch;
import com.callfire.api11.client.api.calls.model.Call;
import com.callfire.api11.client.api.common.model.Label;
import com.callfire.api11.client.api.common.model.Resource;
import com.callfire.api11.client.api.common.model.ResourceException;
import com.callfire.api11.client.api.common.model.ResourceList;
import com.callfire.api11.client.api.common.model.ResourceReference;
import com.callfire.api11.client.api.contacts.model.Contact;
import com.callfire.api11.client.api.contacts.model.ContactHistory;
import com.callfire.api11.client.api.contacts.model.ContactList;
import com.callfire.api11.client.api.subscriptions.model.Subscription;
import com.callfire.api11.client.api.texts.model.AutoReply;
import com.callfire.api11.client.api.texts.model.Text;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Class contains TypeReferences for all model objects
 */
public final class ModelType {
    private static final Map<Class, TypeReference> SIMPLE_TYPES = new HashMap<>();
    private static final Map<Class, TypeReference> LIST_TYPES = new HashMap<>();

    static {
        initSimpleTypes();
        initListTypes();
    }

    private ModelType() {
    }

    private static void initSimpleTypes() {
        // @formatter:off
        SIMPLE_TYPES.put(Object.class, new TypeReference<Object>() {});
        SIMPLE_TYPES.put(ResourceReference.class, new TypeReference<ResourceReference>() {});
        SIMPLE_TYPES.put(ResourceException.class, new TypeReference<ResourceException>() {});

        SIMPLE_TYPES.put(Broadcast.class, new TypeReference<Resource<Broadcast>>() {});
        SIMPLE_TYPES.put(ContactBatch.class, new TypeReference<Resource<ContactBatch>>() {});
        SIMPLE_TYPES.put(BroadcastStats.class, new TypeReference<Resource<BroadcastStats>>() {});
        SIMPLE_TYPES.put(BroadcastSchedule.class, new TypeReference<Resource<BroadcastSchedule>>() {});
        SIMPLE_TYPES.put(Contact.class, new TypeReference<Resource<Contact>>() {});
        SIMPLE_TYPES.put(ContactList.class, new TypeReference<Resource<ContactList>>() {});
        SIMPLE_TYPES.put(ContactHistory.class, new TypeReference<Resource<ContactHistory>>() {});
        SIMPLE_TYPES.put(Call.class, new TypeReference<Resource<Call>>() {});
        SIMPLE_TYPES.put(Text.class, new TypeReference<Resource<Text>>() {});
        SIMPLE_TYPES.put(AutoReply.class, new TypeReference<Resource<AutoReply>>() {});
        SIMPLE_TYPES.put(Subscription.class, new TypeReference<Resource<Subscription>>() {});
        // @formatter:on
    }

    private static void initListTypes() {
        // @formatter:off
        LIST_TYPES.put(Broadcast.class, new TypeReference<ResourceList<Broadcast>>() {});
        LIST_TYPES.put(ContactBatch.class, new TypeReference<ResourceList<ContactBatch>>() {});
        LIST_TYPES.put(BroadcastSchedule.class, new TypeReference<ResourceList<BroadcastSchedule>>() {});
        LIST_TYPES.put(Contact.class, new TypeReference<ResourceList<Contact>>() {});
        LIST_TYPES.put(ContactList.class, new TypeReference<ResourceList<ContactList>>() {});
        LIST_TYPES.put(Call.class, new TypeReference<ResourceList<Call>>() {});
        LIST_TYPES.put(Text.class, new TypeReference<ResourceList<Text>>() {});
        LIST_TYPES.put(AutoReply.class, new TypeReference<ResourceList<AutoReply>>() {});
        LIST_TYPES.put(Subscription.class, new TypeReference<ResourceList<Subscription>>() {});
        LIST_TYPES.put(Label.class, new TypeReference<ResourceList<Label>>() {});
        // @formatter:on
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeReference<T> of(Class<T> type) {
        return safeGet(SIMPLE_TYPES, type);
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeReference<Resource<T>> resourceOf(Class<T> type) {
        return safeGet(SIMPLE_TYPES, type);
    }

    @SuppressWarnings("unchecked")
    public static <T> TypeReference<ResourceList<T>> listOf(Class<T> type) {
        return safeGet(LIST_TYPES, type);
    }

    private static TypeReference safeGet(Map<Class, TypeReference> map, Class type) {
        if (!map.containsKey(type)) {
            throw new IllegalStateException(
                "Map with TypeReferences doesn't contain following type: " + type.getName());
        }
        return map.get(type);
    }
}
