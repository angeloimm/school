import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UtentePageComponent } from './utente-page.component';

describe('UtentePageComponent', () => {
  let component: UtentePageComponent;
  let fixture: ComponentFixture<UtentePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UtentePageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UtentePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
