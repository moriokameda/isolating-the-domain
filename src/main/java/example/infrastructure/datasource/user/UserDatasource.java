package example.infrastructure.datasource.user;

import example.domain.model.user.*;
import example.domain.type.age.DateOfBirth;
import example.domain.type.gender.Gender;
import example.infrastructure.datasource.sequencer.SequencerMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatasource implements UserRepository {
    UserMapper mapper;
    SequencerMapper sequencer;

    @Override
    public User findBy(UserIdentifier id) {
        return mapper.findBy(id);
    }

    @Override
    public UserSummaries list() {
        return new UserSummaries(mapper.list());
    }

    @Override
    public UserCandidate prototype() {
        return new UserCandidate();
    }

    @Override
    public User register(UserCandidate userCandidate) {
        UserIdentifier userId = new UserIdentifier(sequencer.nextVal());
        mapper.registerUser(userId);
        updateName(userId, userCandidate.name());
        updateMailAddress(userId, userCandidate.mailAddress());
        updateDateOfBirth(userId, userCandidate.dateOfBirth());
        updateGender(userId, userCandidate.gender());
        updatePhoneNumber(userId, userCandidate.phoneNumber());
        return findBy(userId);
    }

    @Override
    public void updateName(UserIdentifier identifier, Name name) {
        Long nameId = sequencer.nextVal();
        mapper.registerName(nameId, identifier, name);
        mapper.deleteNameMapper(identifier);
        mapper.registerNameMapper(identifier, nameId);
    }

    @Override
    public void updateMailAddress(UserIdentifier identifier, MailAddress mailAddress) {
        Long mailAddressId = sequencer.nextVal();
        mapper.registerMailAddress(mailAddressId, identifier, mailAddress);
        mapper.deleteMailAddressMapper(identifier);
        mapper.registerMailAddressMapper(identifier, mailAddressId);
    }

    @Override
    public void updateDateOfBirth(UserIdentifier identifier, DateOfBirth dateOfBirth) {
        Long dateOfBirthId = sequencer.nextVal();
        mapper.registerDateOfBirth(dateOfBirthId, identifier, dateOfBirth);
        mapper.deleteDateOfBirthMapper(identifier);
        mapper.registerDateOfBirthMapper(identifier, dateOfBirthId);
    }

    @Override
    public void updateGender(UserIdentifier identifier, Gender gender) {
        Long genderId = sequencer.nextVal();
        mapper.registerGender(genderId, identifier, gender);
        mapper.deleteGenderMapper(identifier);
        mapper.registerGenderMapper(identifier, genderId);
    }

    @Override
    public void updatePhoneNumber(UserIdentifier identifier, PhoneNumber phoneNumber) {
        Long phoneNumberId = sequencer.nextVal();
        mapper.registerPhoneNumber(phoneNumberId, identifier, phoneNumber);
        mapper.deletePhoneNumberMapper(identifier);
        mapper.registerPhoneNumberMapper(identifier, phoneNumberId);
    }

    @Override
    public void delete(User user) {
        mapper.delete(user);
    }

    public UserDatasource(UserMapper mapper, SequencerMapper sequencer) {
        this.mapper = mapper;
        this.sequencer = sequencer;
    }
}
