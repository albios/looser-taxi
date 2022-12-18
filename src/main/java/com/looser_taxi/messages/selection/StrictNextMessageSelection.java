package com.looser_taxi.messages.selection;

import com.looser_taxi.messages.NextMessageSelection;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("strictlyNextSelection")
@RequiredArgsConstructor
public class StrictNextMessageSelection extends NextMessageSelection {

    @Override
    protected int getNextMessageIndex() {
        return 0;
    }
}
