package org.rutebanken.tiamat.importer;

import org.rutebanken.tiamat.model.DataManagedObjectStructure;
import org.rutebanken.tiamat.netex.id.NetexIdHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
public class OriginalIdMatcher {

    private static final Logger logger = LoggerFactory.getLogger(OriginalIdMatcher.class);

        public boolean matchesOnOriginalId(DataManagedObjectStructure dataManagedObject, DataManagedObjectStructure otherDataManagedObject) {

        if (Objects.isNull(dataManagedObject) || Objects.isNull(otherDataManagedObject)) {
            return false;
        }

        if (dataManagedObject.getOriginalIds().isEmpty() && otherDataManagedObject.getOriginalIds().isEmpty()) {
            return false;
        }

        boolean match = compareAfterPrefix(dataManagedObject, otherDataManagedObject);

        if(!match) {
            match = compareNumericPostFix(dataManagedObject, otherDataManagedObject);
        }

        if(match) {
            logger.debug("Object matches on original ID: {}. Existing object ID: {}", dataManagedObject, dataManagedObject.getNetexId());
            return true;
        }

        return match;
    }

    private boolean compareNumericPostFix(DataManagedObjectStructure dataManagedObject, DataManagedObjectStructure otherDataManagedObject) {
        final Set<?> originalIds = convertPostfixToNumber(dataManagedObject.getOriginalIds());
        final Set<?> otherOriginalIds = convertPostfixToNumber(otherDataManagedObject.getOriginalIds());

        return !Collections.disjoint(originalIds, otherOriginalIds);
    }

    private boolean compareAfterPrefix(DataManagedObjectStructure dataManagedObject, DataManagedObjectStructure otherDataManagedObject) {
        final Set<?> originalIds = removePrefixesFromIds(dataManagedObject.getOriginalIds());
        final Set<?> otherOriginalIds = removePrefixesFromIds(otherDataManagedObject.getOriginalIds());

        return !Collections.disjoint(originalIds, otherOriginalIds);
    }

    public Set<Long> convertPostfixToNumber(Set<String> stringOriginalIds) {
        return stringOriginalIds
                .stream()
                .map(netexId -> {
                    try {
                        return NetexIdHelper.extractIdPostfixNumeric(netexId);
                    } catch (NumberFormatException nfe) {
                        logger.info("Cannot parse original ID postfix {} to Integer", netexId);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(toSet());
    }

    private Set<String> removePrefixesFromIds(Set<String> originalIds) {
        Set<String> strippedIds = new HashSet<>(originalIds.size());
        originalIds.forEach(completeId -> {
            if (completeId.contains(":")) {
                strippedIds.add(completeId.substring(completeId.indexOf(':')));
            } else {
                logger.info("Cannot strip prefix from ID {} as it does not contain colon", completeId);
                strippedIds.add(completeId);
            }
        });
        return strippedIds;
    }
}
