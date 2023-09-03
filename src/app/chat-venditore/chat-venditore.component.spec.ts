import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChatVenditoreComponent } from './chat-venditore.component';

describe('ChatVenditoreComponent', () => {
  let component: ChatVenditoreComponent;
  let fixture: ComponentFixture<ChatVenditoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChatVenditoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChatVenditoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
